package com.mycompany.automatisation_project;

import jakarta.persistence.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.List;

@WebServlet("/lignefacture")
public class LigneFactureServlet extends HttpServlet {

    @PersistenceUnit(unitName = "testPU")
    private EntityManagerFactory emf;

    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (EntityManager em = getEntityManager()) {
            LigneFacture ligne = new LigneFacture();
            ligne.setIdArticle(Integer.parseInt(request.getParameter("idArticle")));
            ligne.setQte(Integer.parseInt(request.getParameter("qte")));
            ligne.setPu(Float.parseFloat(request.getParameter("pu")));

            em.getTransaction().begin();
            em.persist(ligne);
            em.getTransaction().commit();

            response.setContentType("text/plain");
            response.getWriter().write("Ligne insérée avec ID : " + ligne.getId());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (EntityManager em = getEntityManager()) {
            String idParam = request.getParameter("id");
            String articleParam = request.getParameter("idArticle");
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");

            if (idParam != null) {
                LigneFacture ligne = em.find(LigneFacture.class, Integer.parseInt(idParam));
                if (ligne != null) {
                    out.printf("{\"id\":%d,\"idArticle\":%d,\"qte\":%d,\"pu\":%.2f}",
                            ligne.getId(), ligne.getIdArticle(), ligne.getQte(), ligne.getPu());
                } else {
                    response.setStatus(404);
                    out.write("{\"error\":\"Ligne non trouvée\"}");
                }
            } else if (articleParam != null) {
                List<LigneFacture> lignes = em.createQuery(
                        "SELECT l FROM LigneFacture l WHERE l.idArticle = :id", LigneFacture.class)
                        .setParameter("id", Integer.parseInt(articleParam))
                        .getResultList();

                out.write("[");
                for (int i = 0; i < lignes.size(); i++) {
                    LigneFacture l = lignes.get(i);
                    out.printf("{\"id\":%d,\"idArticle\":%d,\"qte\":%d,\"pu\":%.2f}%s",
                            l.getId(), l.getIdArticle(), l.getQte(), l.getPu(),
                            (i < lignes.size() - 1) ? "," : "");
                }
                out.write("]");
            } else {
                response.setStatus(400);
                out.write("{\"error\":\"Paramètre manquant (id ou idArticle)\"}");
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (EntityManager em = getEntityManager()) {
            int id = Integer.parseInt(request.getParameter("id"));
            int newQte = Integer.parseInt(request.getParameter("qte"));

            LigneFacture ligne = em.find(LigneFacture.class, id);
            if (ligne == null) {
                response.setStatus(404);
                response.getWriter().write("Ligne introuvable");
                return;
            }

            em.getTransaction().begin();
            ligne.setQte(newQte);
            em.getTransaction().commit();

            response.getWriter().write("Quantité mise à jour");
        }
    }
}
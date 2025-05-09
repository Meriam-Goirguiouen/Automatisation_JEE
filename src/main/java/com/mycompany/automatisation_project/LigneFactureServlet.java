package com.mycompany.automatisation_project;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet("/lignefacture")
public class LigneFactureServlet extends HttpServlet {

    // Simuler une base de données en mémoire
    private static final Map<Integer, LigneFacture> fakeDB = new HashMap<>();
    private static int currentId = 1;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idArticle = Integer.parseInt(request.getParameter("idArticle"));
        int qte = Integer.parseInt(request.getParameter("qte"));
        float pu = Float.parseFloat(request.getParameter("pu"));

        LigneFacture ligne = new LigneFacture();
        ligne.setId(currentId++);
        ligne.setIdArticle(idArticle);
        ligne.setQte(qte);
        ligne.setPu(pu);

        fakeDB.put(ligne.getId(), ligne);

        response.setContentType("text/plain");
        response.getWriter().write("Ligne insérée avec ID : " + ligne.getId());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String idParam = request.getParameter("id");
        String articleParam = request.getParameter("idArticle");

        if (idParam != null) {
            int id = Integer.parseInt(idParam);
            LigneFacture ligne = fakeDB.get(id);
            if (ligne != null) {
                out.printf("{\"id\":%d,\"idArticle\":%d,\"qte\":%d,\"pu\":%.2f}",
                        ligne.getId(), ligne.getIdArticle(), ligne.getQte(), ligne.getPu());
            } else {
                response.setStatus(404);
                out.write("{\"error\":\"Ligne non trouvée\"}");
            }
        } else if (articleParam != null) {
            int idArticle = Integer.parseInt(articleParam);
            List<LigneFacture> lignes = new ArrayList<>();
            for (LigneFacture lf : fakeDB.values()) {
                if (lf.getIdArticle() == idArticle) {
                    lignes.add(lf);
                }
            }

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

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int newQte = Integer.parseInt(request.getParameter("qte"));

        LigneFacture ligne = fakeDB.get(id);
        if (ligne == null) {
            response.setStatus(404);
            response.getWriter().write("Ligne introuvable");
            return;
        }

        ligne.setQte(newQte);
        response.getWriter().write("Quantité mise à jour");
}
}
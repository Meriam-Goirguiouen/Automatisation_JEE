package com.mycompany.servlet;
import com.mycompany.dao.BonLivraisonDAO;
import com.mycompany.model.BonLivraison;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/bon-livraison")
public class BonLivraisonServlet extends HttpServlet {

    private BonLivraisonDAO dao;

    @Override
    public void init() {
        dao = new BonLivraisonDAO();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action manquante");
            return;
        }
        switch (action) {
            case "add":
                addBonLivraison(request, response);
                break;
            case "update":
                updateBonLivraison(request, response);
                break;
            case "delete":
                deleteBonLivraison(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action inconnue");
        }
    }

    private void addBonLivraison(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String date = request.getParameter("date");
        int clientId = Integer.parseInt(request.getParameter("client_id"));
        String etat = request.getParameter("etat");

        BonLivraison bon = new BonLivraison();
        bon.setDate(date);
        bon.setclient_id(clientId);
        bon.setEtat(etat);

        dao.addBonLivraison(bon);
        response.getWriter().write("Bon de livraison ajouté avec succès !");
    }

    private void updateBonLivraison(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        String date = request.getParameter("date");
        int clientId = Integer.parseInt(request.getParameter("client_id"));
        String etat = request.getParameter("etat");

        BonLivraison bon = dao.findById(id);
        if (bon == null) {
            response.getWriter().write("Bon de livraison introuvable.");
            return;
        }

        bon.setDate(date);
        bon.setclient_id(clientId);
        bon.setEtat(etat);

        dao.updateBonLivraison(bon);
        response.getWriter().write("Bon de livraison mis à jour !");
    }

    private void deleteBonLivraison(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));

        dao.deleteBonLivraison(id);
        response.getWriter().write("Bon de livraison supprimé !");
    }

    @Override
    public void destroy() {
        dao.close(); // Fermer EntityManagerFactory
    }
}
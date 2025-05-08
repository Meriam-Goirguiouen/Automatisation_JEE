package com.mycompany.automatisation_project;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Meriam
 */
@WebServlet(name = "LigneBonLivraisonServlet", urlPatterns = {"/LigneBonLivraisonServlet"})
public class LigneBonLivraisonServlet extends HttpServlet {

 // Méthode de validation de la quantité
    private boolean isValidQuantity(int qte) {
        return qte > 0;
    }

    // Méthode de simulation de l'insertion
    private String insertLigneBonLivraison(String libelle, int qte) {
        // On simule une insertion réussie
        if ("error".equals(libelle)) {
            return "Error: Database error simulated.";
        }
        return "LigneBonLivraison inserted successfully!";
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String libelle = request.getParameter("libelle");
        int qte;

        // On récupère la quantité, avec gestion d'exception pour les mauvaises valeurs
        try {
            qte = Integer.parseInt(request.getParameter("qte"));
        } catch (NumberFormatException e) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h2>Error: Invalid quantity format.</h2>");
            return;
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Vérification si la quantité est valide
        if (!isValidQuantity(qte)) {
            out.println("<h2>Quantity must be positive!</h2>");
            return;
        }

        // Simuler l'insertion dans la "base de données"
        String result = insertLigneBonLivraison(libelle, qte);
        out.println("<h2>" + result + "</h2>");

    }
}

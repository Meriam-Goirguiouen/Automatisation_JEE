package com.mycompany.automatisation_project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.mycompany.automatisation_project.ConnectionSQL.getConnection;

@WebServlet("/addFacture")
public class FactureServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Récupérer les données envoyées dans la requête
        String dateFactureStr = request.getParameter("dateFacture"); // format: yyyy-MM-dd
        String client = request.getParameter("client");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // Convertir la date (si nécessaire)
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateFacture = sdf.parse(dateFactureStr);
            java.sql.Date sqlDate = new java.sql.Date(dateFacture.getTime());

            // Connexion à la base
            Connection conn = getConnection();

            // Préparer la requête SQL
            String sql = "INSERT INTO facture (dateFacture, client) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setDate(1, sqlDate);
            stmt.setString(2, client);

            // Affichage debug
            System.out.println("Ajout de facture : " + dateFacture + ", client = " + client);

            // Exécution
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                out.println("<h2>Facture insérée avec succès !</h2>");
            } else {
                out.println("<h2>Échec de l'insertion de la facture.</h2>");
            }

            stmt.close();
            conn.close();

        } catch (Exception e) {
            out.println("<h2>Erreur : " + e.getMessage() + "</h2>");
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
        }
    }
}

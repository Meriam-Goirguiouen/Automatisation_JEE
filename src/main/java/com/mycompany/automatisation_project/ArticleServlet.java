package com.mycompany.automatisation_project;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class ArticleServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        String nom = request.getParameter("nom");
        String ref = request.getParameter("ref");
        String qteStr = request.getParameter("qteDeStock");

        // Basic validation
        if (nom == null || nom.trim().isEmpty()) {
            out.println("Error: 'nom' is required.");
            return;
        }

        if (ref == null || ref.trim().isEmpty()) {
            out.println("Error: 'ref' is required.");
            return;
        }

        int qte;
        try {
            qte = Integer.parseInt(qteStr);
            if (qte < 0) {
                out.println("Error: Quantity must be positive.");
                return;
            }
        } catch (NumberFormatException e) {
            out.println("Error: Invalid stock quantity.");
            return;
        }

        try (Connection conn = ConnectionSQL.getConnection()) {
            String query = "INSERT INTO article (nom, ref, qteDeStock) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, nom);
            stmt.setString(2, ref);
            stmt.setInt(3, qte);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                out.println("Article inserted successfully!");
            } else {
                out.println("Error: Failed to insert article.");
            }

        } catch (SQLIntegrityConstraintViolationException dup) {
            out.println("Error: Duplicate reference.");
        } catch (SQLException e) {
            out.println("Database error: " + e.getMessage());
        }
    }
}

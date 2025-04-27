package com.mycompany.automatisation_project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.mycompany.automatisation_project.ConnectionSQL.getConnection;

@WebServlet("/addArticle")
public class ArticleServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String nom = request.getParameter("nom");
        String ref = request.getParameter("ref");
        int qteDeStock = Integer.parseInt(request.getParameter("qteDeStock"));

        // Set the response type to HTML
        response.setContentType("text/html");

        // Get the PrintWriter object to send the response to the client
        PrintWriter out = response.getWriter();

        try {
            // Connect to the database
            Connection conn = getConnection();

            // Insert article
            String sql = "INSERT INTO articles (nom, ref, qteDeStock) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nom);
            stmt.setString(2, ref);
            stmt.setInt(3, qteDeStock);

            // Log the SQL query for debugging
            System.out.println("Executing query: " + sql);
            System.out.println("Inserting article with values: " + nom + ", " + ref + ", " + qteDeStock);

            // Execute the update and check how many rows were affected
            int rows = stmt.executeUpdate();
            
            // Log the result of the update
            if (rows > 0) {
                System.out.println("Article inserted successfully!");
                conn.commit();  // Commit the transaction if auto-commit is off
                out.println("<h2>Article inserted successfully!</h2>");
            } else {
                System.out.println("Failed to insert article.");
                out.println("<h2>Failed to insert article.</h2>");
            }

            // Close the resources
            stmt.close();
            conn.close();
        } catch (Exception e) {
            out.println("<h2>Error: " + e.getMessage() + "</h2>");
            e.printStackTrace(); // Print stack trace for debugging
            throw new ServletException("DB error", e);
        } finally {
            // Ensure the PrintWriter is flushed and closed
            out.flush();
            out.close();
        }
    }
}



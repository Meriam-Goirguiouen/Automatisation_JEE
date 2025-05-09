package com.mycompany.automatisation_project;

import com.mycompany.automatisation_project.Panier;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class PanierServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String clientParam = request.getParameter("client");
        String dateParam = request.getParameter("date");

        boolean clientValide = false;
        boolean dateValide = false;

        // Vérifier si le client est un entier
        try {
            Integer.parseInt(clientParam);
            clientValide = true;
        } catch (NumberFormatException e) {
            clientValide = false;
        }

        // Vérifier si la date est au bon format
        try {
            LocalDate.parse(dateParam); // format attendu : yyyy-MM-dd
            dateValide = true;
        } catch (DateTimeParseException e) {
            dateValide = false;
        }

        // Envoyer la réponse
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        out.println("Client valide : " + clientValide);
        out.println("Date valide : " + dateValide);
    }
}

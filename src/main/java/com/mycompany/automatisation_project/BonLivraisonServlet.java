package com.mycompany.automatisation_project;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/bonlivraison")
public class BonLivraisonServlet extends HttpServlet {

    // In-memory storage to simulate a database
    private static final Map<String, String> bonLivraisonDatabase = new HashMap<>();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String id = request.getParameter("id");
        String date = request.getParameter("date");
        String clientid = request.getParameter("clientid");
        String etat = request.getParameter("etat");

        PrintWriter out = response.getWriter();
        response.setContentType("text/plain");

        switch (action) {
            case "insert":
                insert(id, date, clientid, etat);
                out.write("Insert successful");
                break;
            case "update":
                update(id, date, clientid, etat);
                out.write("Update successful");
                break;
            case "find":
                String result = find(id);
                out.write("Found: " + result);
                break;
            case "delete":
                delete(id);
                out.write("Delete successful");
                break;
            default:
                out.write("Unknown action");
                break;
        }
    }

    // Insert method
    private void insert(String id, String date, String clientid, String etat) {
        String bonLivraison = "Date: " + date + ", Client: " + clientid + ", Etat: " + etat;
        bonLivraisonDatabase.put(id, bonLivraison);
    }

    // Update method
    private void update(String id, String date, String clientid, String etat) {
        if (bonLivraisonDatabase.containsKey(id)) {
            String bonLivraison = "Date: " + date + ", Client: " + clientid + ", Etat: " + etat;
            bonLivraisonDatabase.put(id, bonLivraison);
        }
    }

    // Find method
    private String find(String id) {
        return bonLivraisonDatabase.getOrDefault(id, "Not found");
    }

    // Delete method
    private void delete(String id) {
        bonLivraisonDatabase.remove(id);
    }
}

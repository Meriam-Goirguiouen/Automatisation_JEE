import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

@WebServlet("/panier")
public class PanierServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String dateStr = request.getParameter("date");
        String clientStr = request.getParameter("client");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Date dateCommande = Date.valueOf(dateStr);
            int client = Integer.parseInt(clientStr);

            Panier panier = new Panier(dateCommande, client);

            out.println("<html><body>");
            out.println("<h2>Résultat du test Panier</h2>");
            out.println("<p>Client valide : " + panier.isValidClient() + "</p>");
            out.println("<p>Date valide : " + panier.isValidDate() + "</p>");
            out.println("</body></html>");
        } catch (Exception e) {
            out.println("<html><body>");
            out.println("<h2>Erreur :</h2>");
            out.println("<p>" + e.getMessage() + "</p>");
            out.println("</body></html>");
        }
    }
}

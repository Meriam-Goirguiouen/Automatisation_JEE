import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import com.mycompany.automatisation_project.LigneBonLivraisonServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

import static org.mockito.Mockito.*;

public class LigneBonLivraisonServletTest {

    private LigneBonLivraisonServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter writer;

    @BeforeEach
    void setUp() throws Exception {
        servlet = new LigneBonLivraisonServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        writer = mock(PrintWriter.class);

        when(response.getWriter()).thenReturn(writer);
    }

    @Test
    void testValidData() throws Exception {
        when(request.getParameter("libelle")).thenReturn("Clavier");
        when(request.getParameter("qte")).thenReturn("10");

        servlet.doPost(request, response);

        // Vérifie si la réponse contient le message d'insertion réussie
        verify(writer).println(contains("LigneBonLivraison inserted successfully"));
    }

    @Test
    void testInvalidQuantityFormat() throws Exception {
        when(request.getParameter("libelle")).thenReturn("Souris");
        when(request.getParameter("qte")).thenReturn("abc");

        servlet.doPost(request, response);

        // Vérifie si la réponse contient le message d'erreur
        verify(writer).println(contains("Invalid quantity format"));
    }

    @Test
    void testNegativeQuantity() throws Exception {
        when(request.getParameter("libelle")).thenReturn("Tapis");
        when(request.getParameter("qte")).thenReturn("-5");

        servlet.doPost(request, response);

        // Vérifie si la réponse contient le message d'erreur
        verify(writer).println(contains("Quantity must be positive"));
    }

    @Test
    void testSimulatedDatabaseError() throws Exception {
        when(request.getParameter("libelle")).thenReturn("error");
        when(request.getParameter("qte")).thenReturn("5");

        servlet.doPost(request, response);

        // Vérifie si la réponse contient le message d'erreur simulé de la base de données
        verify(writer).println(contains("Error: Database error simulated."));
    }
}

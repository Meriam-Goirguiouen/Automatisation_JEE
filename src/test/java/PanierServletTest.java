import com.mycompany.automatisation_project.PanierServlet;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class PanierServletTest {

    @Test
    public void testPanierServletWithValidData() throws Exception {
        // Créer les mocks
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // Simuler les paramètres
        when(request.getParameter("date")).thenReturn("2025-04-30");
        when(request.getParameter("client")).thenReturn("5");

        // Simuler l'objet PrintWriter
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        // Appeler la Servlet
        PanierServlet servlet = new PanierServlet();
        servlet.doGet(request, response);

        pw.flush(); // S'assurer que tout est écrit

        // Vérifier le contenu
        String output = sw.toString();
        assertTrue(output.contains("Client valide : true"));
        assertTrue(output.contains("Date valide : true"));
    }
}

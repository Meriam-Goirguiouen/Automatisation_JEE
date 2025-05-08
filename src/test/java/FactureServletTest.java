import com.mycompany.automatisation_project.FactureServlet;
import com.mycompany.automatisation_project.ConnectionSQL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.mockito.Mockito.*;

class FactureServletTest {

    private FactureServlet factureServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter writer;

    @BeforeEach
    void setUp() throws IOException {
        factureServlet = new FactureServlet();

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);
    }

    @Test
    void testDoPost() throws Exception {
        // Simuler les paramètres envoyés par le formulaire
        when(request.getParameter("dateFacture")).thenReturn("2024-05-01");
        when(request.getParameter("client")).thenReturn("Client001");

        // Simuler la connexion à la base de données
        Connection conn = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);

        when(conn.prepareStatement(any(String.class))).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);  // Simule une insertion réussie

        try (MockedStatic<ConnectionSQL> mockedConnectionSQL = mockStatic(ConnectionSQL.class)) {
            mockedConnectionSQL.when(ConnectionSQL::getConnection).thenReturn(conn);

            // Utiliser la réflexion pour appeler la méthode protected doPost
            Method method = FactureServlet.class.getDeclaredMethod("doPost", HttpServletRequest.class, HttpServletResponse.class);
            method.setAccessible(true);  // Permet l'accès à la méthode protected
            method.invoke(factureServlet, request, response);

            // Vérifier que les bons appels ont été faits
            verify(response).getWriter();
            verify(writer).println(contains("Facture insérée avec succès"));
        }
    }
}
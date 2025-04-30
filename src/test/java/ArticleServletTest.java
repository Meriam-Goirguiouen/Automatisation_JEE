import com.mycompany.automatisation_project.ArticleServlet;
import com.mycompany.automatisation_project.ConnectionSQL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.mockito.Mockito.*;

class ArticleServletTest {

    private ArticleServlet articleServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter stringWriter;
    private PrintWriter writer;

    @BeforeEach
    void setUp() throws IOException {
        // Create a new instance of ArticleServlet before each test
        articleServlet = new ArticleServlet();

        // Mock HttpServletRequest and HttpServletResponse
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        // Prepare StringWriter and Mock the PrintWriter
        stringWriter = new StringWriter();
        writer = mock(PrintWriter.class);  // Mock the PrintWriter here

        // Mock the response.getWriter() to return our mocked PrintWriter
        when(response.getWriter()).thenReturn(writer);
    }

    @Test
    void testDoPost() throws Exception {
        // Mock request parameters
        when(request.getParameter("nom")).thenReturn("OPPO A5");
        when(request.getParameter("ref")).thenReturn("PHONE2X00027");
        when(request.getParameter("qteDeStock")).thenReturn("20");

        // Mock database connection and PreparedStatement
        Connection conn = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);

        // Mock the connection to return a mocked PreparedStatement
        when(conn.prepareStatement(any(String.class))).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);  // Simulate successful insert (1 row inserted)

        // Mock static getConnection method using Mockito.mockStatic
        try (MockedStatic<ConnectionSQL> mockedConnectionSQL = mockStatic(ConnectionSQL.class)) {
            mockedConnectionSQL.when(ConnectionSQL::getConnection).thenReturn(conn);

            // Call the servlet's doPost method
            articleServlet.doPost(request, response);

            // Capture the output printed to the PrintWriter
            String output = stringWriter.toString();

            // Verify the output content
            System.out.println("Test Output: " + output); // This will print to the console

            // Verify that response.getWriter() was called
            verify(response).getWriter();

            // Verify that writer.println was called with the success message
            verify(writer).println(contains("Article inserted successfully!"));
        }
    }
}

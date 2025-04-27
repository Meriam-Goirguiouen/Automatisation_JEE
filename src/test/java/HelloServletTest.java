import java.io.PrintWriter;
import com.mycompany.automatisation_project.HelloWorldServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class HelloServletTest {

    @Test
    public void testHelloServlet() throws Exception {
        // Mocking request and response
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // Mock the PrintWriter returned by response.getWriter()
        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);
        
        // Creating an instance of HelloWorldServlet
        HelloWorldServlet servlet = new HelloWorldServlet(); 
        
        // Calling the doGet method
        servlet.doGet(request, response);
        
        // Verifying that getWriter was called
        verify(response).getWriter();
        // Optionally verify that some writing action was performed, if applicable
        // For example, if your servlet writes something to the response writer:
        verify(writer).write(anyString()); // Replace with actual expected write behavior
    }
}

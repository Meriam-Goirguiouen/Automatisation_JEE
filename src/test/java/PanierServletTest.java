import com.mycompany.automatisation_project.PanierServlet;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class PanierServletTest {

    @Test
    public void testClientValide() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("client")).thenReturn("5");
        when(request.getParameter("date")).thenReturn("2025-05-01");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        PanierServlet servlet = new PanierServlet();
        servlet.doGet(request, response);

        pw.flush();
        String output = sw.toString();
        assertTrue(output.contains("Client valide : true"));
    }

    @Test
    public void testClientInvalide() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("client")).thenReturn("abc");
        when(request.getParameter("date")).thenReturn("2025-05-01");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        PanierServlet servlet = new PanierServlet();
        servlet.doGet(request, response);

        pw.flush();
        String output = sw.toString();
        assertTrue(output.contains("Client valide : false"));
    }

    @Test
    public void testValidDate() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("client")).thenReturn("1");
        when(request.getParameter("date")).thenReturn("2025-05-01");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        PanierServlet servlet = new PanierServlet();
        servlet.doGet(request, response);

        pw.flush();
        String output = sw.toString();
        assertTrue(output.contains("Date valide : true"));
    }

    @Test
    public void testInvalidDate() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("client")).thenReturn("1");
        when(request.getParameter("date")).thenReturn("invalid-date");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        PanierServlet servlet = new PanierServlet();
        servlet.doGet(request, response);

        pw.flush();
        String output = sw.toString();
        assertTrue(output.contains("Date valide : false"));
    }

   
}

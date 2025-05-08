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

    private ArticleServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter writer;

    @BeforeEach
    void setUp() throws Exception {
        servlet = new ArticleServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        writer = mock(PrintWriter.class);

        when(response.getWriter()).thenReturn(writer);
    }

    @Test
    void testValidArticleInsertion() throws Exception {
        when(request.getParameter("nom")).thenReturn("Laptop");
        when(request.getParameter("ref")).thenReturn("LPTP001");
        when(request.getParameter("qteDeStock")).thenReturn("10");

        Connection conn = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);
        when(conn.prepareStatement(any())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);

        try (MockedStatic<ConnectionSQL> mockedConn = mockStatic(ConnectionSQL.class)) {
            mockedConn.when(ConnectionSQL::getConnection).thenReturn(conn);
            servlet.doPost(request, response);
            verify(writer).println(contains("Article inserted successfully !"));
        }
    }

    @Test
    void testMissingName() throws Exception {
        when(request.getParameter("nom")).thenReturn("");
        when(request.getParameter("ref")).thenReturn("REF001");
        when(request.getParameter("qteDeStock")).thenReturn("5");

        servlet.doPost(request, response);
        verify(writer).println(contains("nom"));
    }

    @Test
    void testEmptyRefShouldFail() throws Exception {
        when(request.getParameter("nom")).thenReturn("Phone");
        when(request.getParameter("ref")).thenReturn("");
        when(request.getParameter("qteDeStock")).thenReturn("5");

        servlet.doPost(request, response);
        verify(writer).println(contains("ref"));
    }

    @Test
    void testNegativeStockShouldFail() throws Exception {
        when(request.getParameter("nom")).thenReturn("Phone");
        when(request.getParameter("ref")).thenReturn("REF002");
        when(request.getParameter("qteDeStock")).thenReturn("-10");

        servlet.doPost(request, response);
        verify(writer).println(contains("Quantity must be positive"));
    }

    @Test
    void testInvalidStockFormatShouldFail() throws Exception {
        when(request.getParameter("nom")).thenReturn("Tablet");
        when(request.getParameter("ref")).thenReturn("TAB001");
        when(request.getParameter("qteDeStock")).thenReturn("invalid");

        servlet.doPost(request, response);
        verify(writer).println(contains("Invalid stock quantity"));
    }

    @Test
    void testDuplicateRefShouldFail() throws Exception {
        when(request.getParameter("nom")).thenReturn("Phone");
        when(request.getParameter("ref")).thenReturn("DUPL001");
        when(request.getParameter("qteDeStock")).thenReturn("10");

        Connection conn = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);
        when(conn.prepareStatement(any())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenThrow(new java.sql.SQLIntegrityConstraintViolationException());

        try (MockedStatic<ConnectionSQL> mockedConn = mockStatic(ConnectionSQL.class)) {
            mockedConn.when(ConnectionSQL::getConnection).thenReturn(conn);
            servlet.doPost(request, response);
            verify(writer).println(contains("Duplicate reference"));
        }
    }
}

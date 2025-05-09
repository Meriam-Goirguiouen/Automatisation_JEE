package com.mycompany.automatisation_project;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LigneFactureServletTest {

    private LigneFactureServlet servlet;

    @BeforeAll
    public void setup() throws ServletException {
        // Initialize the servlet
        servlet = new LigneFactureServlet();

        // Simulate setting up EntityManagerFactory for testing
        EntityManagerFactory emf = jakarta.persistence.Persistence.createEntityManagerFactory("testPU");
        servlet.setEntityManagerFactory(emf); // Assuming a setter is available
    }

    @AfterAll
    public void teardown() {
        // Clean up after tests
    }

    @Test
    public void testDoPost_InsertAndFind() throws Exception {
        // Mock HttpServletRequest and HttpServletResponse for doPost
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        // Mocking request parameters for creating a new LigneFacture
        when(request.getParameter("idArticle")).thenReturn("1");
        when(request.getParameter("qte")).thenReturn("5");
        when(request.getParameter("pu")).thenReturn("100");
        when(response.getWriter()).thenReturn(writer);

        // Execute doPost (simulate insertion of LigneFacture)
        servlet.doPost(request, response);

        writer.flush();
        String responseContent = stringWriter.toString();

        // Assert that the response contains success message (e.g., "Ligne insérée")
        assertTrue(responseContent.contains("Ligne insérée"));

        // Now verify if the object was inserted by simulating a doGet request
        HttpServletRequest getRequest = mock(HttpServletRequest.class);
        HttpServletResponse getResponse = mock(HttpServletResponse.class);
        StringWriter getStringWriter = new StringWriter();
        PrintWriter getWriter = new PrintWriter(getStringWriter);

        // Mock the parameter for finding the LigneFacture by its id
        when(getRequest.getParameter("id")).thenReturn("1");
        when(getResponse.getWriter()).thenReturn(getWriter);

        // Execute doGet (simulate fetching of LigneFacture)
        servlet.doGet(getRequest, getResponse);

        getWriter.flush();
        String getResponseContent = getStringWriter.toString();

        // Assert that the response contains the price (pu) of the inserted LigneFacture
        assertTrue(getResponseContent.contains("100.00")); // Assuming price (pu) is returned in the response
    }

    @Test
    public void testDoGet_GetByArticle() throws Exception {
        // Insert a LigneFacture to test the GET method for article lookup
        HttpServletRequest postRequest = mock(HttpServletRequest.class);
        HttpServletResponse postResponse = mock(HttpServletResponse.class);
        StringWriter postStringWriter = new StringWriter();
        PrintWriter postWriter = new PrintWriter(postStringWriter);

        // Mocking request parameters for creating a new LigneFacture
        when(postRequest.getParameter("idArticle")).thenReturn("2");
        when(postRequest.getParameter("qte")).thenReturn("2");
        when(postRequest.getParameter("pu")).thenReturn("150");
        when(postResponse.getWriter()).thenReturn(postWriter);

        // Simulate sending a POST request to insert LigneFacture
        servlet.doPost(postRequest, postResponse);

        postWriter.flush();
        String postResponseContent = postStringWriter.toString();

        // Assert the success message indicating LigneFacture is inserted
        assertTrue(postResponseContent.contains("Ligne insérée"));

        // Simulate GET request to retrieve the LigneFacture by article id
        HttpServletRequest getRequest = mock(HttpServletRequest.class);
        HttpServletResponse getResponse = mock(HttpServletResponse.class);
        StringWriter getStringWriter = new StringWriter();
        PrintWriter getWriter = new PrintWriter(getStringWriter);

        when(getRequest.getParameter("idArticle")).thenReturn("2");
        when(getResponse.getWriter()).thenReturn(getWriter);

        // Execute doGet to retrieve the LigneFacture based on article id
        servlet.doGet(getRequest, getResponse);

        getWriter.flush();
        String getResponseContent = getStringWriter.toString();

        // Assert that the response contains the correct article information (e.g.,
        // article id or quantity)
        assertTrue(getResponseContent.contains("idArticle"));
    }

    @Test
    public void testDoPut_UpdateQuantite() throws Exception {
        // First, insert a LigneFacture for testing
        HttpServletRequest postRequest = mock(HttpServletRequest.class);
        HttpServletResponse postResponse = mock(HttpServletResponse.class);
        StringWriter postStringWriter = new StringWriter();
        PrintWriter postWriter = new PrintWriter(postStringWriter);

        // Mock request parameters to create a new LigneFacture
        when(postRequest.getParameter("idArticle")).thenReturn("3");
        when(postRequest.getParameter("qte")).thenReturn("2");
        when(postRequest.getParameter("pu")).thenReturn("75");
        when(postResponse.getWriter()).thenReturn(postWriter);

        // Simulate sending a POST request to insert LigneFacture
        servlet.doPost(postRequest, postResponse);

        postWriter.flush();
        String postResponseContent = postStringWriter.toString();

        // Assert the success message for LigneFacture insertion
        assertTrue(postResponseContent.contains("Ligne insérée"));

        // Now simulate a PUT request to update the quantity
        HttpServletRequest putRequest = mock(HttpServletRequest.class);
        HttpServletResponse putResponse = mock(HttpServletResponse.class);
        StringWriter putStringWriter = new StringWriter();
        PrintWriter putWriter = new PrintWriter(putStringWriter);

        // Mock parameters for updating quantity
        when(putRequest.getParameter("id")).thenReturn("3");
        when(putRequest.getParameter("qte")).thenReturn("10");
        when(putResponse.getWriter()).thenReturn(putWriter);

        // Execute doPut to update the quantity of the existing LigneFacture
        servlet.doPut(putRequest, putResponse);

        putWriter.flush();
        String putResponseContent = putStringWriter.toString();

        // Assert the success message for updating the quantity
        assertTrue(putResponseContent.contains("Quantité mise à jour"));
    }
}
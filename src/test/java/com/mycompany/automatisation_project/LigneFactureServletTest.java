package com.mycompany.automatisation_project;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.*;
import java.io.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LigneFactureServletTest {

    private LigneFactureServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter responseWriter;
    

    @BeforeEach
    public void setup() throws IOException {
        servlet = new LigneFactureServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        responseWriter = new StringWriter();

        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
    }
    
    @Test
    public void testDoPost_InsertAndFind() throws Exception {
    // Mock des paramètres de la requête pour la création d'une nouvelle LigneFacture
    when(request.getParameter("idArticle")).thenReturn("1");
    when(request.getParameter("qte")).thenReturn("5");
    when(request.getParameter("pu")).thenReturn("100");

    // Exécution de doPost (simuler l'insertion d'une LigneFacture)
    servlet.doPost(request, response);
    
    // Verify response
    assertTrue(responseWriter.toString().matches("Ligne insérée avec ID : \\d+"));
    System.out.println("POST Response : " +responseWriter.toString());  // Debugging line
    }

    
    @Test
    public void testDoGet_GetByArticle() throws Exception {
    // POST pour ajouter une LigneFacture
    when(request.getParameter("idArticle")).thenReturn("2");
    when(request.getParameter("qte")).thenReturn("2");
    when(request.getParameter("pu")).thenReturn("150");

    // Mock du PrintWriter pour la réponse POST
    StringWriter postStringWriter = new StringWriter();
    PrintWriter postWriter = new PrintWriter(postStringWriter);
    when(response.getWriter()).thenReturn(postWriter);

    servlet.doPost(request, response);
    postWriter.flush();
    assertTrue(postStringWriter.toString().contains("Ligne insérée"));

    // GET pour récupérer la LigneFacture par son idArticle
    when(request.getParameter("idArticle")).thenReturn("2");

    // Mock du PrintWriter pour la réponse GET
    StringWriter getStringWriter = new StringWriter();
    PrintWriter getWriter = new PrintWriter(getStringWriter);
    when(response.getWriter()).thenReturn(getWriter);

    servlet.doGet(request, response);
    getWriter.flush();

    String jsonResponse = getStringWriter.toString();
    System.out.println("GET Response JSON: " + jsonResponse);  // Debugging line

    // Vérification que l'idArticle est présent dans la réponse JSON
    assertTrue(jsonResponse.contains("\"idArticle\":2"));
     }
        }

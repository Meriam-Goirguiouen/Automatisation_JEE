package com.mycompany.automatisation_project;

import jakarta.persistence.*;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LigneFactureServletTest {

    private LigneFactureServlet servlet;

    @BeforeEach
    public void setup() {
        servlet = new LigneFactureServlet();

        // Injection manuelle de l'EntityManagerFactory mocké
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("testPU");
        servlet.setEntityManagerFactory(emf);
    }

    @Test
    public void testDoPost_InsertLigneFacture() throws Exception {
        // Simuler la requête et réponse
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(request.getParameter("idArticle")).thenReturn("1");
        when(request.getParameter("qte")).thenReturn("5");
        when(request.getParameter("pu")).thenReturn("99.99");
        when(response.getWriter()).thenReturn(writer);

        servlet.doPost(request, response);

        writer.flush();
        assertTrue(stringWriter.toString().contains("Ligne insérée"));
    }

    @Test
    public void testDoGet_FindById_NotFound() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(request.getParameter("id")).thenReturn("999999"); // ID qui n'existe pas
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);

        writer.flush();
        assertTrue(stringWriter.toString().contains("Ligne non trouvée"));
    }
}
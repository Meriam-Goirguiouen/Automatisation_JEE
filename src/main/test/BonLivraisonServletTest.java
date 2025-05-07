package com.mycompany.test;


import com.mycompany.servlet.BonLivraisonServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

class BonLivraisonServletTest {

    private BonLivraisonServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter responseWriter;

    @BeforeEach
    void setUp() throws Exception {
        servlet = new BonLivraisonServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        responseWriter = new StringWriter();

        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
    }

    @Test
    void testInsert() throws Exception {
        // Setup mock request parameters
        when(request.getParameter("action")).thenReturn("insert");
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("date")).thenReturn("2025-05-07");
        when(request.getParameter("clientid")).thenReturn("123");
        when(request.getParameter("etat")).thenReturn("Pending");

        // Call servlet
        servlet.doPost(request, response);

        // Verify response
        assertEquals("Insert successful", responseWriter.toString());
    }

    @Test
    void testUpdate() throws Exception {
        // Setup mock request parameters
        when(request.getParameter("action")).thenReturn("update");
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("date")).thenReturn("2025-06-01");
        when(request.getParameter("clientid")).thenReturn("123");
        when(request.getParameter("etat")).thenReturn("Delivered");

        // Call servlet
        servlet.doPost(request, response);

        // Verify response
        assertEquals("Update successful", responseWriter.toString());
    }

    @Test
    void testFind() throws Exception {
        // Setup mock request parameters
        when(request.getParameter("action")).thenReturn("find");
        when(request.getParameter("id")).thenReturn("1");

        // Call servlet
        servlet.doPost(request, response);

        // Verify response
        assertEquals("Found: Date: 2025-05-07, Client: 123, Etat: Pending", responseWriter.toString());
    }

    @Test
    void testDelete() throws Exception {
        // Setup mock request parameters
        when(request.getParameter("action")).thenReturn("delete");
        when(request.getParameter("id")).thenReturn("1");

        // Call servlet
        servlet.doPost(request, response);

        // Verify response
        assertEquals("Delete successful", responseWriter.toString());
    }
}

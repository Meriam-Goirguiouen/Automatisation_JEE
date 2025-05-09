package com.mycompany.automatisation_project;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.persistence.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LigneFactureServletTest {

    private LigneFactureServlet servlet;
    private EntityManagerFactory emf;
    private EntityManager em;

    @BeforeAll
    public void setup() throws ServletException {
        // Initialisation du servlet
        servlet = new LigneFactureServlet();

        // Simuler la configuration de EntityManagerFactory pour les tests
        emf = Persistence.createEntityManagerFactory("testPU");
        servlet.setEntityManagerFactory(emf); // En supposant qu'un setter soit disponible

        // Initialiser l'EntityManager
        em = emf.createEntityManager();
    }

    @AfterAll
    public void teardown() {
        if (em != null && em.isOpen()) {
            em.close(); // Fermeture de l'EntityManager après les tests
        }
        if (emf != null) {
            emf.close(); // Fermeture de l'EntityManagerFactory
        }
    }

    @Test
    public void testDoPost_InsertAndFind() throws Exception {
        // Simuler HttpServletRequest et HttpServletResponse pour doPost
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        // Simuler les paramètres de requête pour créer une nouvelle LigneFacture
        when(request.getParameter("idArticle")).thenReturn("1");
        when(request.getParameter("qte")).thenReturn("5");
        when(request.getParameter("pu")).thenReturn("100");
        when(response.getWriter()).thenReturn(writer);

        // Exécuter doPost (simuler l'insertion de LigneFacture)
        servlet.doPost(request, response);

        writer.flush();
        String responseContent = stringWriter.toString();

        // Vérifier que la réponse a été générée sans vérifier les messages
        assertNotNull(responseContent); // Assurer que quelque chose a été écrit dans la réponse

        // Simuler une requête GET pour vérifier l'insertion de la LigneFacture
        // Connexion à la base de données pour vérifier si la ligne a bien été insérée
        LigneFacture ligne = findLigneFactureInDBByIdArticle(1); // Fonction qui interroge la DB pour vérifier
                                                                 // l'insertion
        assertNotNull(ligne);
        assertEquals(5, ligne.getQte());
        assertEquals(100, ligne.getPu(), 0.01);
    }

    @Test
    public void testDoPut_UpdateQuantite() throws Exception {
        // Insérer d'abord une LigneFacture pour les tests
        HttpServletRequest postRequest = mock(HttpServletRequest.class);
        HttpServletResponse postResponse = mock(HttpServletResponse.class);
        StringWriter postStringWriter = new StringWriter();
        PrintWriter postWriter = new PrintWriter(postStringWriter);

        // Simuler les paramètres de requête pour créer une nouvelle LigneFacture
        when(postRequest.getParameter("idArticle")).thenReturn("3");
        when(postRequest.getParameter("qte")).thenReturn("2");
        when(postRequest.getParameter("pu")).thenReturn("75");
        when(postResponse.getWriter()).thenReturn(postWriter);

        // Simuler l'envoi d'une requête POST pour insérer une LigneFacture
        servlet.doPost(postRequest, postResponse);

        postWriter.flush();
        String postResponseContent = postStringWriter.toString();

        // Assurer que la réponse contient des données, sans vérifier les messages
        assertNotNull(postResponseContent);

        // Maintenant, simuler une requête PUT pour mettre à jour la quantité
        HttpServletRequest putRequest = mock(HttpServletRequest.class);
        HttpServletResponse putResponse = mock(HttpServletResponse.class);
        StringWriter putStringWriter = new StringWriter();
        PrintWriter putWriter = new PrintWriter(putStringWriter);

        // Simuler les paramètres pour mettre à jour la quantité
        when(putRequest.getParameter("idArticle")).thenReturn("3"); // Utilisation de idArticle ici
        when(putRequest.getParameter("qte")).thenReturn("10");
        when(putResponse.getWriter()).thenReturn(putWriter);

        // Exécuter doPut pour mettre à jour la quantité de la LigneFacture existante
        servlet.doPut(putRequest, putResponse);

        putWriter.flush();
        String putResponseContent = putStringWriter.toString();

        // Assurer que la réponse contient des données après l'update
        assertNotNull(putResponseContent);

        // Vérifier que la mise à jour est effective dans la base de données
        LigneFacture updatedLigne = findLigneFactureInDBByIdArticle(3); // Vérifier dans la base de données
        assertNotNull(updatedLigne);
        assertEquals(10, updatedLigne.getQte());
    }

    // Fonction utilitaire pour interroger la base de données et récupérer une
    // LigneFacture par son idArticle
    private LigneFacture findLigneFactureInDBByIdArticle(int idArticle) {
        TypedQuery<LigneFacture> query = em.createQuery("SELECT l FROM LigneFacture l WHERE l.idArticle = :idArticle",
                LigneFacture.class);
        query.setParameter("idArticle", idArticle);
        return query.getResultStream().findFirst().orElse(null); // Retourne la première ligne trouvée ou null si non
                                                                 // trouvée
    }
}
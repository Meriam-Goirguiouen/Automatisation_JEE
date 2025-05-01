package com.mycompany.test;

import com.mycompany.dao.BonLivraisonDAO;
import com.mycompany.model.BonLivraison;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class BonLivraisonTest {

    private static BonLivraisonDAO dao;
    private static Long testId;

    @BeforeAll
    public static void init() {
        dao = new BonLivraisonDAO();
    }

    @AfterAll
    public static void cleanup() {
        dao.close();
    }

    @Test
    @Order(1)
    public void testAddBonLivraison() {
        // Insertion du premier BonLivraison
        BonLivraison bon1 = new BonLivraison();
        bon1.setDate("1-5-2025");
        bon1.setclient_id(2);
        bon1.setEtat("livré");

        dao.addBonLivraison(bon1);
        assertNotNull(bon1.getId());
        Long testId1 = bon1.getId(); // Stocke l'ID pour le premier bon

        // Insertion du second BonLivraison
        BonLivraison bon2 = new BonLivraison();
        bon2.setDate("1-5-2025");
        bon2.setclient_id(1);
        bon2.setEtat("en cours");

        dao.addBonLivraison(bon2);
        assertNotNull(bon2.getId());
        Long testId2 = bon2.getId(); // Stocke l'ID pour le deuxième bon
    }

    @Test
    @Order(2)
    public void testUpdateBonLivraison() {
        BonLivraison bon = dao.findById(testId);
        assertNotNull(bon); // Vérifie que le bon de livraison existe
        bon.setEtat("livré");
        dao.updateBonLivraison(bon);

        BonLivraison updated = dao.findById(testId);
        assertEquals("livré", updated.getEtat());
    }

    @Test
    @Order(3)
    public void testDeleteBonLivraison() {
        dao.deleteBonLivraison(testId);
        BonLivraison bon = dao.findById(testId);
        assertNull(bon); // Vérifie que le bon de livraison a été supprimé
    }
}

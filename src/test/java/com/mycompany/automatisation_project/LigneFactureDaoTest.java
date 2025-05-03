package com.mycompany.automatisation_project;

import jakarta.persistence.*;
import org.junit.jupiter.api.*;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LigneFactureDaoTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    private LigneFactureDAO dao;

    @BeforeAll
    public void setup() {
        emf = Persistence.createEntityManagerFactory("testPU");
        em = emf.createEntityManager();
        dao = new LigneFactureDAO(em);
    }

    @AfterAll
    public void teardown() {
        em.close();
        emf.close();
    }

    @Test
    public void testInsertAndFind() {
        LigneFacture ligne = new LigneFacture();
        ligne.setIdArticle(1);
        ligne.setQte(5);
        ligne.setPu(100);

        dao.insert(ligne);
        LigneFacture found = dao.find(ligne.getId());

        Assertions.assertNotNull(found);
        Assertions.assertEquals(5, found.getQte());
    }

    @Test
    public void testGetByArticle() {
        LigneFacture ligne = new LigneFacture();
        ligne.setIdArticle(2);
        ligne.setQte(3);
        ligne.setPu(50);
        dao.insert(ligne);

        List<LigneFacture> list = dao.getByArticle(2);
        Assertions.assertFalse(list.isEmpty());
    }

    @Test
    public void testUpdateQuantite() {
        LigneFacture ligne = new LigneFacture();
        ligne.setIdArticle(3);
        ligne.setQte(2);
        ligne.setPu(75);
        dao.insert(ligne);

        boolean updated = dao.updateQuantite(ligne.getId(), 10);
        LigneFacture updatedLine = dao.find(ligne.getId());

        Assertions.assertTrue(updated);
        Assertions.assertEquals(10, updatedLine.getQte());
    }
}
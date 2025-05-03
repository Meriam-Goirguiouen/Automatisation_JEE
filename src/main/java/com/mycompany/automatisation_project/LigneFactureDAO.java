package com.mycompany.automatisation_project;

import jakarta.persistence.*;
import java.util.List;

public class LigneFactureDAO {

    private EntityManager em;

    public LigneFactureDAO(EntityManager em) {
        this.em = em;
    }

    public LigneFacture insert(LigneFacture ligne) {
        em.getTransaction().begin();
        em.persist(ligne);
        em.getTransaction().commit();
        return ligne;
    }

    public LigneFacture find(int id) {
        return em.find(LigneFacture.class, id);
    }

    public List<LigneFacture> getByArticle(int idArticle) {
        return em.createQuery("SELECT l FROM LigneFacture l WHERE l.idArticle = :id", LigneFacture.class)
                .setParameter("id", idArticle)
                .getResultList();
    }

    public boolean updateQuantite(int id, int newQte) {
        LigneFacture ligne = find(id);
        if (ligne == null)
            return false;
        em.getTransaction().begin();
        ligne.setQte(newQte);
        em.getTransaction().commit();
        return true;
    }
}
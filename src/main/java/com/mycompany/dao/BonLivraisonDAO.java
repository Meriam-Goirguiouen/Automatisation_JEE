package com.mycompany.dao;

import com.mycompany.model.BonLivraison;
import jakarta.persistence.*;

public class BonLivraisonDAO {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("testPU");

    // ➕ Ajouter un bon de livraison
    public void addBonLivraison(BonLivraison bon) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(bon);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erreur lors de l'ajout", e);
        } finally {
            em.close();
        }
    }

    // 📝 Mettre à jour un bon de livraison
    public void updateBonLivraison(BonLivraison bon) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(bon);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erreur lors de la mise à jour", e);
        } finally {
            em.close();
        }
    }

    // ❌ Supprimer un bon de livraison par ID
    public void deleteBonLivraison(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            BonLivraison bon = em.find(BonLivraison.class, id);
            if (bon != null) {
                em.remove(bon);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erreur lors de la suppression", e);
        } finally {
            em.close();
        }
    }

    // 🔍 Rechercher un bon de livraison par ID
    public BonLivraison findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(BonLivraison.class, id);
        } finally {
            em.close();
        }
    }

    // 🚪 Fermer le EntityManagerFactory (à utiliser en fin d'application)
    public void close() {
        emf.close();
    }
}

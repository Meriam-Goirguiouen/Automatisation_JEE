package com.mycompany.automatisation_project;

import java.sql.Date;
import java.time.LocalDate;

public class Panier {
    private int id;
    private Date dateCommande;
    private int client;

    // Constructeur avec tous les champs
    public Panier(int id, Date dateCommande, int client) {
        this.id = id;
        this.dateCommande = dateCommande;
        this.client = client;
    }

    // Constructeur sans id (si id est auto-généré)
    public Panier(Date dateCommande, int client) {
        this.dateCommande = dateCommande;
        this.client = client;
    }

    // Getters
    public int getId() {
        return id;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public int getClient() {
        return client;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public void setClient(int client) {
        this.client = client;
    }

    // Vérifie si l'identifiant client est valide (> 0)
    public boolean isValidClient() {
        return client > 0;
    }

    // Vérifie si la date de commande n'est pas dans le futur
    public boolean isValidDate() {
        LocalDate currentDate = LocalDate.now();
        LocalDate commandeDate = dateCommande.toLocalDate();
        return !commandeDate.isAfter(currentDate); // La date ne doit pas être dans le futur
    }
}

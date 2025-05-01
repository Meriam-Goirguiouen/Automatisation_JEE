package com.mycompany.model;

import jakarta.persistence.*;

@Entity
@Table(name = "bon_livraison")
public class BonLivraison {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;

    @Column(name = "id_client")
    private int client_id;

    private String etat;

    // Getters
    public Long getId() { return id; }
    public String getDate() { return date; }
    public int getclient_id() { return client_id; }
    public String getEtat() { return etat; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setDate(String date) { this.date = date; }
    public void setclient_id(int client_id) { this.client_id = client_id; }
    public void setEtat(String etat) { this.etat = etat; }
}

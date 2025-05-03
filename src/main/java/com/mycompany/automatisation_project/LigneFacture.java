package com.mycompany.automatisation_project;

import jakarta.persistence.*;

@Entity
@Table(name = "db_lignefacture")
public class LigneFacture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int idArticle;
    private int qte;
    private float pu;

    // Getters and Setters
    public int getId() { return id; }

    public int getIdArticle() { return idArticle; }
    public void setIdArticle(int idArticle) { this.idArticle = idArticle; }

    public int getQte() { return qte; }
    public void setQte(int qte) { this.qte = qte; }

    public float getPu() { return pu; }
    public void setPu(float pu) { this.pu = pu; }
}
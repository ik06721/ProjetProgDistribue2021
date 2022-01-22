package com.ecommerce.microservicepaiement;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Paiement {

    @Id
    private int id;
    @Column(unique = true)
    private int idCommande;
    private int montant;
    private long numeroCarte;
    

    public Paiement() {
    }

    public Paiement(int id, int idCommande, int montant, long numeroCarte) {
        this.id = id;
        this.idCommande = idCommande;
        this.montant = montant;
        this.numeroCarte = numeroCarte;
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public long getNumeroCarte() {
        return numeroCarte;
    }

    public void setNumeroCarte(long numeroCarte) {
        this.numeroCarte = numeroCarte;
    }

    @Override
    public String toString() {
        return "Paiement{" +
                "id=" + id +
                ", idCommande=" + idCommande +
                ", montant=" + montant +
                ", numeroCarte=" + numeroCarte +
                '}';
    }
}
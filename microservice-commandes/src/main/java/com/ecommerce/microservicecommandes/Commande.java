package com.ecommerce.microservicecommandes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

@Entity
public class Commande {

    @Id
    private int id;

    private int productId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dateCommande;

    private int quantite;

    private boolean commandePayee;

    public Commande() {
    }

    public Commande(int id, int productId, Date dateCommande, int quantite, boolean commandePayee) {
        this.id = id;
        this.productId = productId;
        this.dateCommande = dateCommande;
        this.quantite = quantite;
        this.commandePayee = commandePayee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public boolean getCommandePayee() {
        return commandePayee;
    }

    public void setCommandePayee(boolean commandePayee) {
        this.commandePayee = commandePayee;
    }

    @Override
    public String toString() {
        return "commande{" +
                "id=" + id +
                ", productId=" + productId +
                ", dateCommande=" + dateCommande +
                ", quantite=" + quantite +
                ", commandePayee=" + commandePayee +
                '}';
    }
}
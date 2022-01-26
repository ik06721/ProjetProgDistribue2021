package com.clientui;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ClientController {

    @Autowired
    private MicroserviceProduitsProxy ProduitsProxy;

    @Autowired
    private MicroserviceCommandeProxy CommandesProxy;

    @Autowired
    private MicroservicePaiementProxy paiementProxy;


    /*
    * Étape (1)
    * Opération qui récupère la liste des produits et on les affichent dans la page d'accueil.
    * Les produits sont récupérés grâce à ProduitsProxy
    * On fini par rentourner la page Accueil.html à laquelle on passe la liste d'objets "produits" récupérés.
    * */
    @RequestMapping("/")
    public String accueil(Model model){
    	
        List<ProductBean> produits =  ProduitsProxy.listeDesProduits();
        model.addAttribute("produits", produits);
        
        return "Accueil";
    }

    /*
    * Étape (2)
    * Opération qui récupère les détails d'un produit
    * On passe l'objet "produit" récupéré et qui contient les détails en question à  FicheProduit.html
    * */
    @RequestMapping("/details-produit/{id}")
    public String ficheProduit(@PathVariable int id,  Model model){

        ProductBean produit = ProduitsProxy.recupererUnProduit(id);
        model.addAttribute("produit", produit);

        return "FicheProduit";
    }

    /*
    * Étape (3) et (4)
    * Opération qui fait appel au microservice de commande pour placer une commande et récupérer les détails de la commande créée
    * */
    @RequestMapping(value = "/commander-produit/{idProduit}/{montant}")
    public String passerCommande(@PathVariable int idProduit, @PathVariable int montant,  Model model){

        CommandeBean commande = new CommandeBean();
        Random random = new Random();
        int nbAleatoire = random.nextInt(1000 + 1) + 1;
        
        //On renseigne les propriétés de l'objet de type CommandeBean que nous avons crée
        commande.setId(nbAleatoire);
        commande.setProductId(idProduit);
        commande.setQuantite(1);
        commande.setDateCommande(new Date());
        commande.setCommandePayee(false);
        
        //appel du microservice commandes grâce à Feign et on récupère en retour les détails de la commande créée, notamment son ID (étape 4).
        CommandeBean commandeAjoutee = CommandesProxy.ajouterCommande(commande);

        //on passe à la vue l'objet commande et le montant de celle-ci afin d'avoir les informations nécessaire pour le paiement
        model.addAttribute("commande", commandeAjoutee);
        model.addAttribute("montant", montant);
        
        return "Paiement";
    }

    /*
    * Étape (5)
    * Opération qui fait appel au microservice de paiement pour traiter un paiement
    * */
    @RequestMapping(value = "/payer-commande/{idCommande}/{montantCommande}")
    public String payerCommande(@PathVariable int idCommande, @PathVariable int montantCommande, Model model){

        PaiementBean paiementAExecuter = new PaiementBean();
        
        Random random = new Random();
        int nbAleatoire = random.nextInt(1000 + 1) + 1;
        //on reseigne les détails du produit
        paiementAExecuter.setId(nbAleatoire);
        paiementAExecuter.setIdCommande(idCommande);
        paiementAExecuter.setMontant(montantCommande);
        paiementAExecuter.setNumeroCarte(numcarte()); // on génère un numéro au hasard pour simuler une CB

        // On appel le microservice et (étape 7) on récupère le résultat qui est sous forme ResponseEntity<PaiementBean> ce qui va nous permettre de vérifier le code retour.
        ResponseEntity<PaiementBean> paiement = paiementProxy.payerUneCommande(paiementAExecuter);

        Boolean etatPaiement = false;
        //si le code est autre que 201 CREATED, c'est que le paiement n'a pas pu aboutir.
        if(paiement.getStatusCode() == HttpStatus.CREATED) {
        		etatPaiement = true;
                CommandeBean commande = CommandesProxy.recupererUneCommande(idCommande);
                commande.setCommandePayee(true);
                CommandesProxy.updateCommande(commande); 
        }
        
        else {
        		//paiementProxy.supprimerPaiement(idCommande);
        		CommandesProxy.supprimerCommande(idCommande);
        }
        
        model.addAttribute("paiementOk", etatPaiement); // on envoi un Boolean paiementAccepté à la vue
        return "confirmation";
    }

    //Génére une serie de 16 chiffres au hasard pour simuler vaguement une CB
    private long numcarte() {
        return ThreadLocalRandom.current().nextLong(1000000000000000L,9000000000000000L );
    }
}

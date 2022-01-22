package com.ecommerce.microservicepaiement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PaiementController {

    @Autowired
    PaiementDao paiementDao;
    
    @PostMapping(value = "/paiements")
    public ResponseEntity<Paiement>  payerUneCommande(@RequestBody Paiement paiement){
        //Vérifions s'il y a déjà un paiement enregistré pour cette commande
        Paiement paiementExistant = paiementDao.findByidCommande(paiement.getIdCommande());
        if(paiementExistant != null) 
        	throw new PaiementExistantException("Cette commande est déjà payée");

        //Enregistrer le paiement
        Paiement nouveauPaiement = paiementDao.save(paiement);

        if(nouveauPaiement == null) 
        	throw new PaiementImpossibleException("Erreur, impossible d'établir le paiement, réessayez plus tard");

        //TODO Nous allons appeler le Microservice Commandes ici pour lui signifier que le paiement est accepté

        return new ResponseEntity<Paiement>(nouveauPaiement, HttpStatus.CREATED);
    }
    
   
	@GetMapping(value = "/paiements")
	public List<Paiement> listeDesPaiements() {
		List<Paiement> paiements = paiementDao.findAll();
		
		if(paiements.isEmpty()) 
			throw new PaiementExistantException("Erreur, il n'existe aucun paiement");

		return paiements;
		
	}
    
    @DeleteMapping(value = "/paiements/{id}")
	public void supprimerPaiement(@PathVariable int id) {
		paiementDao.deleteById(id);
	}
	
	@PutMapping(value = "/paiements")
	public void updatePaiement(@RequestBody Paiement paiement) {
		paiementDao.save(paiement);
	}
	
	/**
	@Query("SELECT id,nom,prix FROM Product p WHERE p.prix > :prixLimit")
		List<Product> chercherUnProduitCher(@Param("prixLimit") int prix); **/	

}
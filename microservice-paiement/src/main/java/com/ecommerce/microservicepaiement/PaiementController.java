package com.ecommerce.microservicepaiement;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

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
    
   
	@GetMapping("/paiements")
	public MappingJacksonValue listeProduits() {
		Iterable<Paiement> paiements = paiementDao.findAll();
		
		if(paiementDao.findAll().isEmpty()) 
			throw new PaiementExistantException("Erreur, il n'existe aucun paiement");
		
		SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("numeroCarte");
		FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);
		MappingJacksonValue paiementsFiltres = new MappingJacksonValue(paiements);
		paiementsFiltres.setFilters(listDeNosFiltres);
		return paiementsFiltres;
		
	}
    
    @DeleteMapping(value = "/paiements/{id}")
	public void supprimerProduit(@PathVariable int id) {
		paiementDao.deleteById(id);
	}
	
	@PutMapping(value = "/paiements")
	public void updateProduit(@RequestBody Paiement paiement) {
		paiementDao.save(paiement);
	}
	
	/**
	@Query("SELECT id,nom,prix FROM Product p WHERE p.prix > :prixLimit")
		List<Product> chercherUnProduitCher(@Param("prixLimit") int prix); **/	

}
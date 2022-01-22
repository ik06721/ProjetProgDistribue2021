package com.ecommerce.microservicecommandes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CommandeController {

    @Autowired
    CommandesDao commandesDao;

    @PostMapping (value = "/commandes")
    public ResponseEntity<Commande> ajouterCommande(@RequestBody Commande commande){

        Commande nouvelleCommande = commandesDao.save(commande);

        if(nouvelleCommande == null) throw new ImpossibleAjouterCommandeException("Impossible d'ajouter cette commande");

        return new ResponseEntity<Commande>(commande, HttpStatus.CREATED);
    }

    @GetMapping(value = "/commandes/{id}")
    public Optional<Commande> recupererUneCommande(@PathVariable int id){

        Optional<Commande> commande = commandesDao.findById(id);

        if(!commande.isPresent()) throw new CommandeNotFoundException("Cette commande n'existe pas");

        return commande;
    }
    
    @GetMapping(value = "/commandes")
	public List<Commande> listeDesCommandes() {
		List<Commande> commandes = commandesDao.findAll();
		
		if(commandes.isEmpty()) 
			throw new CommandeNotFoundException("Erreur, il n'existe aucune commande");
		
		return commandes;
    }
    
    @DeleteMapping(value = "/commandes/{id}")
	public void supprimerCommande(@PathVariable int id) {
		commandesDao.deleteById(id);
	}
	
	@PutMapping(value = "/commandes")
	public void updateCommande(@RequestBody Commande commande) {
		commandesDao.save(commande);
	}
}
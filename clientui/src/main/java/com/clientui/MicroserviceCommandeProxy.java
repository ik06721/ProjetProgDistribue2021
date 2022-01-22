package com.clientui;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "microservice-commandes", url = "localhost:9092")
public interface MicroserviceCommandeProxy {

    @PostMapping(value = "/commandes")
    CommandeBean ajouterCommande(@RequestBody CommandeBean commande);
    
    @PutMapping(value = "/commandes")
	void updateCommande(@RequestBody CommandeBean commande);
    
    @GetMapping( value = "/commandes/{id}")
    CommandeBean recupererUneCommande(@PathVariable("id") int id);
    
    @DeleteMapping(value = "commmandes/{id}")
    void supprimerCommande(@PathVariable ("id") int id);

}

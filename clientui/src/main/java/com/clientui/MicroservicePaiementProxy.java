
package com.clientui;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "microservice-paiement")
@LoadBalancerClient(name = "microservice-paiement")
public interface MicroservicePaiementProxy {

    @PostMapping(value = "/paiements")
    ResponseEntity<PaiementBean> payerUneCommande(@RequestBody PaiementBean paiement);
    
    @DeleteMapping(value = "/paiements/{id}")
    void supprimerPaiement(@PathVariable ("id") int id);
    
}

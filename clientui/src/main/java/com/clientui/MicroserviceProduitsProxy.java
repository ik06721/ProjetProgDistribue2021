package com.clientui;

import java.util.List;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservice-produits")
@LoadBalancerClient(name = "microservice-produits")
public interface MicroserviceProduitsProxy {

    @GetMapping(value = "/Produits")
    List<ProductBean> listeDesProduits();

    /*
    * Notez ici la notation @PathVariable("id") qui est différente de celle qu'on utlise dans le contrôleur
    **/
    @GetMapping( value = "/Produits/{id}")
    ProductBean recupererUnProduit(@PathVariable("id") int id);
    
}

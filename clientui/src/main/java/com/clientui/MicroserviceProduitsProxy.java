package com.clientui;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservice-produits", url = "localhost:9090")
public interface MicroserviceProduitsProxy {

@GetMapping(value = "/Produits")
List<ProductBean> listeDesProduits();

@GetMapping( value = "/Produits/{id}")
ProductBean recupererUnProduit(@PathVariable("id") int id);
	
}



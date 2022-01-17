package com.ecommerce.microcommerce.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.web.dao.ProductDao;
import com.ecommerce.microcommerce.web.exceptions.ProductNotFoundException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

@RestController
public class ProductController {

	@Autowired
	private ProductDao productDao;
		
	@GetMapping("/Produits")
	public List<Product> listeDesProduits(){
		List<Product> products = productDao.findAll();
		
		if(products.isEmpty()) 
			throw new ProductNotFoundException("Aucun produit n'est disponible à la vente");
		
		return products;
	}
	
	/**
	@GetMapping("/Produits")
	public MappingJacksonValue listeProduits() {
		Iterable<Product> products = productDao.findAll();
		
		if(products.isEmpty()) 
			throw new ProductNotFoundException("Aucun produit n'est disponible à la vente");
		
		
		SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");
		FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);
		MappingJacksonValue produitsFiltres = new MappingJacksonValue(products);
		produitsFiltres.setFilters(listDeNosFiltres);
		return produitsFiltres;
		
	} **/
	
	
	@GetMapping("/Produits/{id}")
	public Optional<Product> recupererUnProduit(@PathVariable int id) {
		Optional<Product> product = productDao.findById(id);
		if(!product.isPresent())
			throw new ProductNotFoundException("Le produit correspondant à l'id " + id + " n'existe pas");
		return product;
	}
	
	
	@PostMapping(value = "/Produits")
	public ResponseEntity<Product> ajouterProduit(@RequestBody Product product) {
		Product productAdded = productDao.save(product);
		if(Objects.isNull(productAdded)) 
			return ResponseEntity.noContent().build();
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(productAdded.getId())
				.toUri();
		return ResponseEntity.created(location).build(); 
	}
	
	@DeleteMapping(value = "/Produits/{id}")
	public void supprimerProduit(@PathVariable int id) {
		productDao.deleteById(id);
	}
	
	@PutMapping(value = "/Produits")
	public void updateProduit(@RequestBody Product product) {
		productDao.save(product);
	}
	
	/**
	@Query("SELECT id,nom,prix FROM Product p WHERE p.prix > :prixLimit")
		List<Product> chercherUnProduitCher(@Param("prixLimit") int prix); **/
}
	
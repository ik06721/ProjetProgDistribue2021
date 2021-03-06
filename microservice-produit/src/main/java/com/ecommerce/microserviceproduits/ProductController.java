package com.ecommerce.microserviceproduits;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
public class ProductController {

	@Autowired
	private ProductDao productDao;
	
	
	@RequestMapping(value = "/Produits", method = RequestMethod.GET)
	public List<Product> listeDesProduits() {
		List<Product> products = productDao.findAll();
		
		if(products.isEmpty()) 
			throw new ProductNotFoundException("Aucun produit n'est disponible à la vente");
		
		return products;
		
	} 
	
	@GetMapping(value = "/Produits/{id}")
	public Product recupererUnProduit(@PathVariable int id) {
		Product product = productDao.findById(id);
		if(product == null)
			throw new ProductNotFoundException("Le produit correspondant à l'id " + id + " n'existe pas");
		return product;
	}
	
	
	@PostMapping(value = "/Produits")
	public ResponseEntity<Product> ajouterProduit(@RequestBody Product product) {
		Product productAdded = productDao.save(product);
		
		if(productAdded == null) 
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
	
package com.ecommerce.microserviceproduits;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao extends JpaRepository<Product,Integer> {
	Product findById(int id);
	List<Product> findByPrixGreaterThan(int prixLimit);
}
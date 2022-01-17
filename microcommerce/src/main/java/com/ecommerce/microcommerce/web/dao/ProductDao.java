package com.ecommerce.microcommerce.web.dao;

import com.ecommerce.microcommerce.model.Product;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao extends JpaRepository<Product,Integer> {
}
package com.musinsa.project.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musinsa.project.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findAll();
	List<Product> findByCategory(String category);
	List<Product> findByBrandAndCategory(String brand, String category);
	Product save(Product product);
	void delete(Product product);	
}

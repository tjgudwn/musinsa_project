package com.musinsa.project.repository;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.musinsa.project.product.entity.Product;
import com.musinsa.project.product.repository.ProductRepository;

@DataJpaTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class ProductRepositoryTest {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Test
	public void findAllTest() {
		//when
		List<Product> productList = productRepository.findAll();
		
		//then
		Assertions.assertEquals(productList.size(), 72);
	}
	
	@Test
	public void findByCategoryTest() {
		//when
		List<Product> productList = productRepository.findByCategory("상의");
		
		//then
		Assertions.assertEquals(productList.size(), 9);
		
	}
	
	@Test
	public void findByBrandAndCategoryTest() {
		//when
		List<Product> productList = productRepository.findByBrandAndCategory("A", "상의");
		
		//then
		Assertions.assertEquals(productList.get(0).getPrice(), 11200);
		
	}
}

package com.musinsa.project.service;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.musinsa.project.product.dto.ProductRequest;
import com.musinsa.project.product.entity.Product;
import com.musinsa.project.product.exception.ErrorCode;
import com.musinsa.project.product.exception.ProductException;
import com.musinsa.project.product.repository.ProductRepository;
import com.musinsa.project.product.service.ProductService;

@SpringBootTest
public class ProductServiceTest {
	
	@Autowired
	ProductService productService;
	
	@Autowired
	ProductRepository productRepository;
	
	@Test
	public void getLowPriceBrandByCategoryTest() {
		
		//when
		List<Product> productList = productService.getLowPriceBrandByCategory();
		
		//then
		Assertions.assertTrue(productList.size() > 0);
	}
	
	@Test
	public void getLowPriceBrandTest() {
		
		//when
		List<Product> productList = productService.getLowPriceBrand();
		
		//then
		Assertions.assertNotEquals(productList.size(), 0);
	}
	
	@Test
	public void getBrandByCategoryTest() {
		
		//when
		List<Product> productList = productService.getBrandByCategory("상의");
		
		//then
		Assertions.assertEquals(productList.get(productList.size() - 1).getBrand(), "I");		
	}

	@Test
	public void getBrandByCategoryFailTest() {	
		
		//when then
	    try {
			List<Product> productList = productService.getBrandByCategory("자동차");
	    } catch (ProductException e) {
	        Assertions.assertEquals(ErrorCode.INVALID_PARAMETER.getMessage(), e.getErrorCode().getMessage());
	    }
	}
		
	@Test
	public void saveProductTest() {
		
		//given
		ProductRequest productRequest = new ProductRequest();
		productRequest.setBrand("A");
		productRequest.setCategory("액세서리");
		productRequest.setPrice(1000);
		
		//when
		productService.saveProduct(productRequest);
		List<Product> productList = productService.getBrandByCategory("액세서리");
		
		//then
		Assertions.assertEquals(productList.get(0).getBrand(), "A");	
	}
	
	@Test
	public void saveProductFailTest() {
		
		//given
		ProductRequest productRequest = new ProductRequest();
		productRequest.setBrand("A");
		
		//when then
	    try {
			productService.saveProduct(productRequest);
	    } catch (ProductException e) {
	        Assertions.assertEquals(ErrorCode.INVALID_PARAMETER.getMessage(), e.getErrorCode().getMessage());
	    }
	}
		
	@Test
	public void deleteProductTest() {
		
		//given
		ProductRequest productRequest = new ProductRequest();
		productRequest.setBrand("A");
		productRequest.setCategory("액세서리");
		
		//when
		productService.deleteProduct(productRequest);
		List<Product> productList = productService.getBrandByCategory("액세서리");
		
		//then
		Assertions.assertEquals(productList.size(), 8);
	}

	@Test
	public void deleteProductFailTest() {		
		
		//given
		ProductRequest productRequest = new ProductRequest();
		productRequest.setBrand("A");
		productRequest.setCategory("자동차");
		
		//when then
	    try {
			productService.deleteProduct(productRequest);
	    } catch (ProductException e) {
	        Assertions.assertEquals(ErrorCode.PRODUCT_NOT_FOUND.getMessage(), e.getErrorCode().getMessage());
	    }
	}
}

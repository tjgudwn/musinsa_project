package com.musinsa.project.product.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.musinsa.project.coufiguration.SwaggerConfig;
import com.musinsa.project.product.dto.ProductRequest;
import com.musinsa.project.product.entity.Product;
import com.musinsa.project.product.exception.ErrorCode;
import com.musinsa.project.product.exception.ProductException;
import com.musinsa.project.product.repository.ProductRepository;
import com.musinsa.project.product.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	
	private String minPriceBrand = "";

	@Autowired
	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	@Override
	public List<Product> getLowPriceBrandByCategory() {
		List<Product> productList = productRepository.findAll();
		Map<String, Product> categoryMap = new HashMap<>(); 
		
		for(Product product : productList) {
			if(categoryMap.containsKey(product.getCategory())) {
				if(categoryMap.get(product.getCategory()).getPrice() > product.getPrice()) {
					categoryMap.put(product.getCategory(), product);
				}
			} else {
				categoryMap.put(product.getCategory(), product);
			}
		}
		
		List<Product> resultList = new ArrayList<>(categoryMap.values());
		resultList.sort((o1, o2) -> o2.getPrice() - o1.getPrice());
				
		return resultList;
	}
	
	@Override
	public List<Product> getLowPriceBrand() {
		
		List<Product> productList = productRepository.findAll();
		Map<String, Integer> brandPriceMap = new HashMap<>();
		
		for(Product product : productList) {
			brandPriceMap.put(product.getBrand(), brandPriceMap.getOrDefault(product.getBrand(), 0) + product.getPrice());
		}
		
		int minPrice = Integer.MAX_VALUE;
		
		for(String brand : brandPriceMap.keySet()) {
			if(brandPriceMap.get(brand) < minPrice) {
				minPrice = brandPriceMap.get(brand);
				minPriceBrand = brand;
			}
		}
		
		return productList.stream().filter(o1 -> o1.getBrand().equals(minPriceBrand)).collect(Collectors.toList());
	}
	
	@Override
	public List<Product> getBrandByCategory(String category) {
		List<Product> productList = productRepository.findByCategory(category);
		
		if(productList.size() == 0) {
			throw new ProductException(ErrorCode.INVALID_PARAMETER);
		}
		
		productList.sort((o1, o2) -> o1.getPrice() - o2.getPrice());
		return productList;
	}
	
	@Override
	public void saveProduct(ProductRequest productRequest) {
		if(productRequest.getBrand() == null || productRequest.getCategory() == null) {
			throw new ProductException(ErrorCode.INVALID_PARAMETER);
		}
		
		int price = productRequest.getPrice();
		
		if(price == 0) {
			throw new ProductException(ErrorCode.INVALID_PARAMETER);
		}
		
		List<Product> productList = productRepository.findByBrandAndCategory(productRequest.getBrand(), productRequest.getCategory());
		Product product = new Product();
		
		if(productList.size() > 0) {		
			if(price == productList.get(0).getPrice()) {
				throw new ProductException(ErrorCode.ALREADY_SAVED_PRODUCT);
			}			
		} 
		
		product.setBrand(productRequest.getBrand());
		product.setCategory(productRequest.getCategory());
		product.setPrice(price);
		productRepository.save(product);
	}
	
	@Override
	public void deleteProduct(ProductRequest productRequest) {
		List<Product> productList = productRepository.findByBrandAndCategory(productRequest.getBrand(), productRequest.getCategory());
		
		if(productList.size() > 0) {
			productRepository.delete(productList.get(0));
		} else {
			throw new ProductException(ErrorCode.PRODUCT_NOT_FOUND);
		}
	}
}

package com.musinsa.project.product.service;

import java.util.List;

import com.musinsa.project.product.dto.ProductRequest;
import com.musinsa.project.product.entity.Product;

public interface ProductService {	
	List<Product> getLowPriceBrandByCategory();
	List<Product> getLowPriceBrand();
	List<Product> getBrandByCategory(String category);
	void saveProduct(ProductRequest productRequset);
	void deleteProduct(ProductRequest productRequset);
}

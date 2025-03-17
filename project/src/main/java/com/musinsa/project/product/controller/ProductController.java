package com.musinsa.project.product.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.musinsa.project.product.dto.ProductRequest;
import com.musinsa.project.product.dto.ProductResponseDto;
import com.musinsa.project.product.entity.Product;
import com.musinsa.project.product.exception.ErrorCode;
import com.musinsa.project.product.exception.ProductException;
import com.musinsa.project.product.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class ProductController {
	private final ProductService productService;
	
	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@ResponseBody
	@RequestMapping(value="/product-list/find-by-category", method=RequestMethod.GET)
	@Operation(summary = "카테고리 별 최저가 브랜드 검색", description = "카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API")
	public ResponseEntity<ProductResponseDto> findListByCategory() {
		
		List<Product> productList = productService.getLowPriceBrandByCategory();
		int totalPrice = 0;
		
		for(Product product : productList) {
			totalPrice = totalPrice + product.getPrice();
		}
		
		return new ResponseEntity<>(ProductResponseDto.builder().productList(productList).totalPrice(totalPrice).build(), HttpStatus.OK);
	}	
	
	@ResponseBody
	@RequestMapping(value="/product-list/find-brand", method=RequestMethod.GET)
	@Operation(summary = "최저가격 브랜드 검색", description = "단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API")
	public ResponseEntity<ProductResponseDto> findBrand() {
		
		List<Product> productList = productService.getLowPriceBrand();
		int totalPrice = 0;
		List<Product> productResultList = new ArrayList<>();
		
		String brand = productList.get(0).getBrand();
		
		for(Product product : productList) {
			totalPrice = totalPrice + product.getPrice();
			product.setBrand(null);
			productResultList.add(product);
		}
		
		return new ResponseEntity<>(ProductResponseDto.builder().brand(brand).productList(productList).totalPrice(totalPrice).build(), HttpStatus.OK);
	}	
	
	@ResponseBody
	@RequestMapping(value="/product/find-by-category", method=RequestMethod.GET)
	@Operation(summary = "카테고리 별 상품 검색", description = "카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API")
	public ResponseEntity<ProductResponseDto> findByCategory(@RequestParam(value="category", required = true) String category) {
		
		List<Product> productList = productService.getBrandByCategory(category);
		Product lowPriceProduct = productList.get(0);
		lowPriceProduct.setCategory(null);
		Product highPriceProduct = productList.get(productList.size() - 1);
		highPriceProduct.setCategory(null);
		
		return new ResponseEntity<>(ProductResponseDto.builder().category(category).lowPrice(lowPriceProduct).highPrice(highPriceProduct).build(), HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value="/product/update", method=RequestMethod.POST)
	@Operation(summary = "상품 추가", description = "기존에 없는 상품은 추가하고 기존에 있는 상품은 업데이트 하는 API")
	public ResponseEntity<ProductResponseDto> update(ProductRequest productRequset) {
		
		productService.saveProduct(productRequset);

		return new ResponseEntity<>(ProductResponseDto.builder().message("success").build(), HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value="/product/delete", method=RequestMethod.POST)
	@Operation(summary = "상품 삭제", description = "상품을 삭제하는 API")
	public ResponseEntity<ProductResponseDto> delete(ProductRequest productRequset) {

		productService.deleteProduct(productRequset);

		return new ResponseEntity<>(ProductResponseDto.builder().message("success").build(), HttpStatus.OK);
	}

    @ExceptionHandler(ProductException.class)
    public ResponseEntity<String> handle(ProductException e) {
    	return new ResponseEntity<>(e.getErrorCode().getMessage(), HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handle(Exception e) {
    	return new ResponseEntity<>(ErrorCode.INTERNAL_SERVER_ERROR.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

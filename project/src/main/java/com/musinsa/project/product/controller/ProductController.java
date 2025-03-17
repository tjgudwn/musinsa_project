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
	@Operation(summary = "ī�װ� �� ������ �귣�� �˻�", description = "ī�װ� �� �������� �귣��� ��ǰ ����, �Ѿ��� ��ȸ�ϴ� API")
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
	@Operation(summary = "�������� �귣�� �˻�", description = "���� �귣��� ��� ī�װ� ��ǰ�� ������ �� �������ݿ� �Ǹ��ϴ� �귣��� ī�װ��� ��ǰ����, �Ѿ��� ��ȸ�ϴ� API")
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
	@Operation(summary = "ī�װ� �� ��ǰ �˻�", description = "ī�װ� �̸����� ����, �ְ� ���� �귣��� ��ǰ ������ ��ȸ�ϴ� API")
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
	@Operation(summary = "��ǰ �߰�", description = "������ ���� ��ǰ�� �߰��ϰ� ������ �ִ� ��ǰ�� ������Ʈ �ϴ� API")
	public ResponseEntity<ProductResponseDto> update(ProductRequest productRequset) {
		
		productService.saveProduct(productRequset);

		return new ResponseEntity<>(ProductResponseDto.builder().message("success").build(), HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(value="/product/delete", method=RequestMethod.POST)
	@Operation(summary = "��ǰ ����", description = "��ǰ�� �����ϴ� API")
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

package com.musinsa.project.product.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.musinsa.project.product.entity.Product;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponseDto {
	private String message;
	private String brand;
	private List<Product> productList;
	private Integer totalPrice;
	private String category;
	private Product lowPrice;
	private Product highPrice;	
}

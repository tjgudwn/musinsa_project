package com.musinsa.project.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
	private String brand;
	private String category;
	private Integer price;
}

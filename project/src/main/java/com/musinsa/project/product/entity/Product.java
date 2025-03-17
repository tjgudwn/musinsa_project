package com.musinsa.project.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Product")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
	
    @Column(nullable = false, length = 30)
	String brand;
    
    @Column(nullable = false, length = 30)
	String category;
    
    @Column(nullable = false)
	Integer price;
}

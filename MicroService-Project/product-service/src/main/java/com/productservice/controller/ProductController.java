package com.productservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.productservice.dao.ProductRequest;
import com.productservice.dao.ProductResponse;
import com.productservice.repository.ProductRepository;
import com.productservice.service.ProductService;



@RestController
@RequestMapping("/api/product")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public void createProduct(@RequestBody ProductRequest productRequest){
		//make DTO in order to take the product data from the client not 
		//use directly Product here in controller also donot write buisness logic 
		// in the controller use to write Service layer for this in other pakage; 
		productService.createProduct(productRequest);//this will create end point 
		//for creating product
	}
	
	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public List<ProductResponse> getAllProduct() {
		
		List<ProductResponse> allProducts = productService.getAllProducts();
		return allProducts;
	}
	
}

package com.productservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.productservice.dao.ProductRequest;
import com.productservice.dao.ProductResponse;
import com.productservice.model.Product;
import com.productservice.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	//Using Autowiring to instantiate ProductResponse to take Product Items into 
	//ProductResponse 
//	@Autowired
//	private ProductResponse productResponse;

	public void createProduct(ProductRequest productRequest) {
		//this method takes the ProductRequest as method para and then use this obj to 
		//map to Product model and it is then stored into mongodb
		Product product=Product.builder()
				.name(productRequest.getName())
				.description(productRequest.getDescription())
				.price(productRequest.getPrice())
				.build();
		productRepository.save(product);
//		log.info("product"+ product.getId()+" is saved");
		//or we can also do this
		log.info("product {} is saved",product.getId());

	}

	public List<ProductResponse> getAllProducts() {
		
		List<Product> allProducts = productRepository.findAll();
		
		List<ProductResponse> listOfProducts = allProducts.stream().map(product->mapToProductResponse(product)).toList();		
		return listOfProducts;
	}

	private ProductResponse mapToProductResponse(Product product) {
		// TODO Auto-generated method stub
		return ProductResponse.builder()
				.id(product.getId())
				.name(product.getName())
				.description(product.getDescription())
				.price(product.getPrice())
				.build()
				;
	}
}

package com.orderservice.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.orderservice.dao.OrderRequest;
import com.orderservice.orderservice.OrderService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RestController
@RequestMapping("/api/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
//	@CircuitBreaker(name="inventory",fallbackMethod = "fallbackMethod")
//	@TimeLimiter(name="inventory")
//	@Retry(name="inventory")
	public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest) {
	
		return CompletableFuture.supplyAsync(()->orderService.placeOrder(orderRequest));
		//this will be executed in new thread and after timeout is reached it will stop and throw timeout excp
	}
	
	public CompletableFuture<String> fallbackMethod(OrderRequest orderRequest,RuntimeException exception) {
		return CompletableFuture.supplyAsync(()->"OOPs Something went wrong, please try again Later !");
		
	}
}

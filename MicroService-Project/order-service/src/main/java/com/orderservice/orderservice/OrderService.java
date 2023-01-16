package com.orderservice.orderservice;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import com.orderservice.dao.InventoryResponse;
import com.orderservice.dao.OrderLineItemsDto;
import com.orderservice.dao.OrderRequest;
import com.orderservice.event.OrderPlacedEvent;
import com.orderservice.model.Order;
import com.orderservice.model.OrderLineItems;
import com.orderservice.repository.OrderRepository;

@Service
@Transactional
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private WebClient webClient;
	
	@Autowired
	private KafkaTemplate<String,OrderPlacedEvent> kafkaTemplate;
	
	//orderequest coming form the controller
	public String placeOrder(OrderRequest orderRequest) {
		Order order=new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		
		
		List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtosList()
		.stream()
		.map(orderLineItemsDto -> mapToDto(orderLineItemsDto))
		.toList();
		
		order.setOrderLineItems(orderLineItems);
		//call inventory service  and place order if product in inventory
		// and also reduce quantity in Inventory
		
		List<String> skucode = order.getOrderLineItems().stream().map(orderLineItem->orderLineItem.getSkucode())
		.toList();
		
		
		InventoryResponse[] inventoryResponseArray = webClient.get()
		.uri("http://localhost:9117/api/inventory/",uriBuilder->uriBuilder.queryParam("skucode", skucode).build())
		.retrieve()
		.bodyToMono(InventoryResponse[].class)//to read the data from webclient we use this
		.block();//to make sync request to inventory end point
		
		boolean allProductInStock = Arrays.stream(inventoryResponseArray).allMatch(inventoryResponse->inventoryResponse
				.isInStock());
		
		
		if(allProductInStock) {
			
			orderRepository.save(order);
			kafkaTemplate.send("notificationTopic",new OrderPlacedEvent(order.getOrderNumber()));
			return "Order Placed Successfuly !";
		}
		else {
	
			throw new IllegalArgumentException("Prodcut not available in Inventroy, please try again later");
		}
		
		
	}
	private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
		OrderLineItems orderLineItems=new OrderLineItems();
		
		orderLineItems.setPrice(orderLineItemsDto.getPrice());
		orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
		orderLineItems.setSkucode(orderLineItemsDto.getSkucode());
		
		return orderLineItems;
	}
}

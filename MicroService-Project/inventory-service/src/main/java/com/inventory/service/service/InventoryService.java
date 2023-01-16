package com.inventory.service.service;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inventory.service.dto.InventoryResponse;
import com.inventory.service.model.Inventory;
import com.inventory.service.repository.InventoryRepository;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;



@Service
@Slf4j
public class InventoryService {

	@Autowired
	private InventoryRepository inventoryRepository;
	
	@Transactional(readOnly=true)
	@SneakyThrows
	public List<InventoryResponse> isInstock(List<String> skucode) {
	
		log.info("wait started");
		Thread.sleep(10000);
		log.info("wait ended");
		
		List<Inventory> findBySkuCode = inventoryRepository.findBySkucodeIn(skucode);
	    List<InventoryResponse> list = findBySkuCode.stream().map(inventory->
		
		
		InventoryResponse.builder()
		.skuCode(inventory.getSkucode())
		.isInStock(inventory.getQuantity()>0)
		.build()
				).toList();
	    
	    if(list.isEmpty()) {
	    
	    	throw new IllegalArgumentException("What U are trying to get is not"
	    			+ " in stock")
	    			;
	    	
	    }
	    
	    return list;
	}
}

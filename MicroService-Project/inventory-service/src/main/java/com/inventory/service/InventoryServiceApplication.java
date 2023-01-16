package com.inventory.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import com.inventory.service.model.Inventory;
import com.inventory.service.repository.InventoryRepository;

@SpringBootApplication
@EnableEurekaClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
		return args->{
			Inventory inventory=new Inventory();
			inventory.setSkucode("Apple_Ipone_14");
			inventory.setQuantity(1);
			
			Inventory inventory1=new Inventory();
			inventory1.setSkucode("Samsung_Galaxy_13");
			inventory1.setQuantity(1);
			
			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory1);
		};
	}
}

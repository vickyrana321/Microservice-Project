package com.inventory.service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.service.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long>{

//	Optional<Inventory> findBySkuCode(String skucode);

	

	List<Inventory> findBySkucodeIn(List<String> skucode);
	

	
}

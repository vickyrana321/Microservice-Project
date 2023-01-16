package com.orderservice.dao;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItemsDto {

	private Long id;
	private String skucode;
	private BigDecimal price;
	private Integer quantity;
}

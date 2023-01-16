package com.orderservice.dao;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

import com.orderservice.model.Order;
import com.orderservice.model.OrderLineItems;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

	List<OrderLineItemsDto> orderLineItemsDtosList;
}

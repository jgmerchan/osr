package com.osr.order.domain.order;

import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
public class OrderItem {

	private UUID productId;
	private String description;
	private Money unitPrice;
	private int quantity;
	
	public OrderItem(UUID procutid, String description, Money unitPrice, int quantity) {
		this.productId = procutid;
		this.description = description;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
	}
	
	public OrderItem(String productId, String description, Money unitPrice, int quantity) {
		this(UUID.fromString(productId), description, unitPrice, quantity);
	}
	
}

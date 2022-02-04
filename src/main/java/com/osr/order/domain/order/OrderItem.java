package com.osr.order.domain.order;

import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
public class OrderItem {

	private OrderItemId orderItemId;
	private OrderId orderId;
	private UUID productId;
	private String description;
	private Money unitPrice;
	private int quantity;

	public OrderItem() {

	}

	public OrderItem(UUID orderItemId, UUID procutId, String description, Money unitPrice, int quantity) {
		this.orderItemId = new OrderItemId(orderItemId);
		this.productId = procutId;
		this.description = description;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
	}
	
	public OrderItem(OrderId orderId, UUID procutId, String description, Money unitPrice, int quantity) {
		this(UUID.randomUUID(), procutId, description, unitPrice, quantity);

		this.orderId = orderId;
	}

	public OrderItem(UUID procutId, String description, Money unitPrice, int quantity) {
		this(UUID.randomUUID(), procutId, description, unitPrice, quantity);
	}

	public OrderItem(String productId, String description, Money unitPrice, int quantity) {
		this(UUID.randomUUID(), UUID.fromString(productId), description, unitPrice, quantity);
	}

	public OrderItem(String orderItemId, String productId, String description, Money unitPrice, int quantity) {
		this(UUID.fromString(orderItemId), UUID.fromString(productId), description, unitPrice, quantity);
	}

}

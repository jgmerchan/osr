package com.osr.order.infrastructure.rest.order.response;

import java.util.UUID;

import com.osr.order.domain.order.Order;

import lombok.Getter;

@Getter
public class UpdateOrderResponse {

	private UUID id;
	private Order.OrderStatus status;

	public UpdateOrderResponse(UUID id, Order.OrderStatus status) {
		this.id = id;
		this.status = status;
	}

}

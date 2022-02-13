package com.osr.order.infrastructure.rest.order.response;

import java.util.UUID;

import lombok.Getter;

@Getter
public class CreateOrderResponse {

	private UUID id;
	
	public CreateOrderResponse(UUID id) {
		this.id = id;
	}
}

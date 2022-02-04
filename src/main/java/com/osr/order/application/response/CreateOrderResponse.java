package com.osr.order.application.response;

import java.util.UUID;

import lombok.Getter;

@Getter
public class CreateOrderResponse {

	private UUID id;
	
	public CreateOrderResponse(UUID id) {
		this.id = id;
	}
}

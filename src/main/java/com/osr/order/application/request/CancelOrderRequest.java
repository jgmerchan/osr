package com.osr.order.application.request;

import java.util.UUID;

import lombok.Getter;

@Getter
public class CancelOrderRequest {

	private UUID id;

	public CancelOrderRequest(UUID id) {
		this.id = id;
	}
	
	public CancelOrderRequest() {
		
	}
	
}

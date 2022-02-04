package com.osr.order.application.request;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.Getter;

@Getter
public class UpdateOrderRequest {

	@NotNull
	private UUID id;
	
	public UpdateOrderRequest() {
		
	}
	
	public UpdateOrderRequest(UUID id) {
		this.id = id;
	}
}

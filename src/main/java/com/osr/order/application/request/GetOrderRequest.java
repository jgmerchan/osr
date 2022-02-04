package com.osr.order.application.request;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.Getter;

@Getter
public class GetOrderRequest {

	@NotNull
	private UUID id;

	public GetOrderRequest() {

	}

	public GetOrderRequest(UUID id) {
		this.id = id;
	}
	
}

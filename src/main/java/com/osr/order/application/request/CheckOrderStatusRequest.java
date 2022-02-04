package com.osr.order.application.request;

import java.util.UUID;

import lombok.Getter;

@Getter
public class CheckOrderStatusRequest {

	private UUID id;
	
	public CheckOrderStatusRequest(UUID id) {
		this.id = id;
	}
	
	public CheckOrderStatusRequest() {
		
	}
	
}

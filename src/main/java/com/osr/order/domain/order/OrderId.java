package com.osr.order.domain.order;

import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
public class OrderId {

	private UUID id;
	
	public OrderId() {
		this.id = UUID.randomUUID();
	}
	
	public OrderId(UUID id) {
		this.id = id;
	}
}

package com.osr.order.domain.order;

import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
public class OrderItemId {

private UUID id;
	
	public OrderItemId() {
		this.id = UUID.randomUUID();
	}
	
	public OrderItemId(UUID id) {
		this.id = id;
	}
}

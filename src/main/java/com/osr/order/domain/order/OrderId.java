package com.osr.order.domain.order;

import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class OrderId {

	private UUID id;
	
	public OrderId() {
		this.id = UUID.randomUUID();
	}
}

package com.osr.order.infrastructure.rest.order.response;

import com.osr.order.domain.order.Order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetOrderResponse {

	private Order order;
}

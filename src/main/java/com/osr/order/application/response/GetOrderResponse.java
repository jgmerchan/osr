package com.osr.order.application.response;

import com.osr.order.domain.order.Order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetOrderResponse {

	private Order order;
}

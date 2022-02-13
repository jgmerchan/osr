package com.osr.order.application.usescases.order;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.osr.order.domain.order.Order;
import com.osr.order.domain.order.repository.OrderRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CheckOrderStatusUseCase {

	private final GetOrderUseCase getOrderUseCase;

	private final OrderRepository orderRepository;

	public Order checkOrderStatus(UUID orderId) {
		Order order = getOrderUseCase.getOrderById(orderId);

		if (modifiedOrderIsOverThanFiveMinutes(order)) {
			order.changeToNextStatus();

			orderRepository.update(order);
		}

		return order;
	}

	private boolean modifiedOrderIsOverThanFiveMinutes(Order order) {
		long durantion = new Date().getTime() - order.getModified().getTime();

		return TimeUnit.MILLISECONDS.toMinutes(durantion) > 5;
	}

}

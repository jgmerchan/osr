package com.osr.order.domain.order.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.osr.order.domain.order.Address;
import com.osr.order.domain.order.Order;
import com.osr.order.domain.order.OrderId;
import com.osr.order.domain.order.OrderItem;
import com.osr.order.domain.order.repository.OrderRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

	private OrderRepository orderRepository;

	@Override
	public UUID createOrder(Order.PaymentMethod paymentMethod, Address addres, List<OrderItem> orderItems) {
		if (hasNotOrderItems(orderItems)) {
			throw new OrderServiceException("The order must have at leat one order item");
		}

		if (hasNotPaymentMethod(paymentMethod)) {
			throw new OrderServiceException("The payment mehtod must be informed");
		}

		Order order = new Order(paymentMethod, addres);

		orderItems.forEach(orderItem -> order.addOrderItem(orderItem));

		orderRepository.save(order);

		return order.getOrderId().getId();
	}

	private boolean hasNotOrderItems(List<OrderItem> orderItems) {
		return orderItems == null || orderItems.isEmpty();
	}

	private boolean hasNotPaymentMethod(Order.PaymentMethod paymentMethod) {
		return paymentMethod == null;
	}

	@Override
	public Order changeNextStatusOrder(UUID orderId) {
		Order order = findById(orderId);

		order.changeToNextStatus();

		orderRepository.update(order);

		return order;
	}

	@Override
	public void cancelOrder(UUID orderId) {
		Order order = findById(orderId);

		order.changeStatusToCancelled();

		orderRepository.update(order);
	}

	private Order findById(UUID orderId) {
		if (orderIdIsNull(orderId)) {
			throw new OrderServiceException("The order identifier must be informed");
		}

		return orderRepository.findById(new OrderId(orderId))
				.orElseThrow(() -> new OrderServiceException("The order does not exists"));
	}

	private boolean orderIdIsNull(UUID orderId) {
		return orderId == null;
	}

	@Override
	public Order checkStatus(UUID orderId) {
		Order order = findById(orderId);

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

	@Override
	public Order getOrderById(UUID orderId) {
		return findById(orderId);
	}

}

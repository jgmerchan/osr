package com.osr.order.domain.order.service;

import java.util.List;
import java.util.UUID;

import com.osr.order.domain.order.Address;
import com.osr.order.domain.order.Order;
import com.osr.order.domain.order.OrderItem;
import com.osr.order.domain.order.repository.OrderRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

	private OrderRepository orderRepository;
	
	@Override
	public void createOrder(Order.PaymentMethod paymentMethod, Address addres, List<OrderItem> orderItems) {
		if(hasNotOrderItems(orderItems)) {
			throw new OrderServiceException("The order must have at leat one order item");
		}
		
		if(hasNotPaymentMethod(paymentMethod)) {
			throw new OrderServiceException("The payment mehtod must be informed");
		}
		
		Order order = new Order(paymentMethod, addres);
		
		orderItems.forEach(orderItem -> order.addOrderItem(orderItem));
		
		orderRepository.save(order);
	}
	
	private boolean hasNotOrderItems(List<OrderItem> orderItems) {
		return orderItems == null || orderItems.isEmpty();
	}
	
	private boolean hasNotPaymentMethod(Order.PaymentMethod paymentMethod) {
		return paymentMethod == null;
	}
	
	@Override
	public void changeNextStatusOrder(UUID orderId) {
		Order order = findById(orderId);
		
		order.changeToNextStatus();
		
		orderRepository.save(order);
	}
	
	@Override
	public void cancelOrder(UUID orderId) {
		
		
	}

	@Override
	public Order findById(UUID orderId) {
		if(orderIdIsNull(orderId)) {
			throw new OrderServiceException("The order identifier must be informed");
		}
		
		return orderRepository.findById(orderId).orElseThrow(() -> new OrderServiceException("The order does not exists"));
	}
	
	private boolean orderIdIsNull(UUID orderId) {
		return orderId == null;
	}
	
}

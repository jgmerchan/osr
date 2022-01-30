package com.osr.order.domain.service;

import java.util.List;
import java.util.UUID;

import com.osr.order.domain.order.Address;
import com.osr.order.domain.order.Order;
import com.osr.order.domain.order.OrderItem;

public interface OrderService {
	void createOrder(Order.PaymentMethod paymentMethod, Address addres, List<OrderItem> orderItems);
	
	void changeNextStatusOrder(UUID orderId);
	
	void cancelOrder(UUID orderId);
	
	Order findById(UUID orderId);
}

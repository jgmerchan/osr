package com.osr.order.domain.order.service;

import java.util.List;
import java.util.UUID;

import com.osr.order.domain.order.Address;
import com.osr.order.domain.order.Order;
import com.osr.order.domain.order.OrderItem;

public interface OrderService {
	UUID createOrder(Order.PaymentMethod paymentMethod, Address addres, List<OrderItem> orderItems);
	
	Order changeNextStatusOrder(UUID orderId);
	
	void cancelOrder(UUID orderId);
	
	Order checkStatus(UUID orderId);
	
	Order getOrderById(UUID orderId);
}

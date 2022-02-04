package com.osr.order.domain.order.repository;

import java.util.Optional;

import com.osr.order.domain.order.Order;
import com.osr.order.domain.order.OrderId;

public interface OrderRepository {

	Optional<Order> findById(OrderId orderId);
	
	void save(Order order);
	
	void update(Order order);
}

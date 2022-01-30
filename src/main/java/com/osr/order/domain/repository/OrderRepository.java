package com.osr.order.domain.repository;

import java.util.Optional;
import java.util.UUID;

import com.osr.order.domain.order.Order;

public interface OrderRepository {

	Optional<Order> findById(UUID id);
	
	void save(Order order);
}

package com.osr.order.infrastructure.repository;

import com.osr.order.domain.order.Order;
import com.osr.order.domain.order.OrderId;
import com.osr.order.domain.order.repository.OrderRepository;
import com.osr.order.infrastructure.repository.converter.OrderConverter;
import com.osr.order.infrastructure.repository.entities.OrderEntity;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class H2OderRepository implements OrderRepository {

	private final SpringDataH2DbOrderRepository orderrepository;
	private final OrderConverter orderConverter;

	@Override
	public Optional<Order> findById(OrderId id) {
		Optional<OrderEntity> orderEntity = orderrepository.findById(id.getId());

		return orderEntity.map(oe -> orderConverter.toDomain(oe, oe.getOrderItems()));
	}

	@Override
	public void save(Order order) {
		orderrepository
				.save(orderConverter.toEntity(order, true, order.getOrderItems()));
	}

	@Override
	public void update(Order order) {
		orderrepository
				.save(orderConverter.toEntity(order, false, order.getOrderItems()));
	}

}

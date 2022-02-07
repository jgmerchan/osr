package com.osr.order.infrastructure.repository;

import java.util.Optional;

import com.osr.order.domain.order.Order;
import com.osr.order.domain.order.OrderId;
import com.osr.order.domain.order.repository.OrderRepository;
import com.osr.order.infrastructure.repository.converter.OrderConverter;
import com.osr.order.infrastructure.repository.entities.OrderEntity;

public class H2OderRepository implements OrderRepository {

	private final SpringDataH2DbOrderRepository orderrepository;

	public H2OderRepository(SpringDataH2DbOrderRepository orderrepository) {
		this.orderrepository = orderrepository;
	}

	@Override
	public Optional<Order> findById(OrderId id) {
		Optional<OrderEntity> orderEntity = orderrepository.findById(id.getId());

		return orderEntity.map(oe -> OrderConverter.toDomain(oe, oe.getOrderItems()));
	}

	@Override
	public void save(Order order) {
		orderrepository.save(OrderConverter.toEntity(order, true, order.getOrderItems()));
	}

	@Override
	public void update(Order order) {
		orderrepository.save(OrderConverter.toEntity(order, false, order.getOrderItems()));
	}

}

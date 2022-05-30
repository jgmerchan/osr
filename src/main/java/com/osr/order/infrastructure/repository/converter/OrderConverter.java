package com.osr.order.infrastructure.repository.converter;

import com.osr.order.domain.order.Address;
import com.osr.order.domain.order.Order;
import com.osr.order.domain.order.Order.OrderStatus;
import com.osr.order.domain.order.Order.PaymentMethod;
import com.osr.order.domain.order.OrderItem;
import com.osr.order.infrastructure.repository.entities.OrderEntity;
import com.osr.order.infrastructure.repository.entities.OrderItemEntity;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class OrderConverter {

	private OrderItemConverter orderItemConverter;

	public Order toDomain(OrderEntity orderEntity, List<OrderItemEntity> orderItems) {
		Order order = new Order(orderEntity.getId(), 
				OrderStatus.valueOf(orderEntity.getStatus()),
				PaymentMethod.valueOf(orderEntity.getPaymentMethod()), 
				orderEntity.getDate(),
				orderEntity.getModified(),
				new Address(
						orderEntity.getAddress(),
						orderEntity.getCity(), 
						orderEntity.getZipCode()
				));
		
		orderItems.forEach(oi -> order.addOrderItem(orderItemConverter.toDomain(oi)));
		
		return order;
	}
	
	
	public OrderEntity toEntity(Order order, boolean isNew, List<OrderItem> orderItems) {
		List<OrderItemEntity> orderItemsEntity = orderItems.stream()
				.map(oi -> orderItemConverter.toEntity(oi, order.getOrderId(), isNew))
				.collect(Collectors.toList());
		
		return new OrderEntity(order.getOrderId().getId(), 
				order.getStatus().toString(),
				order.getPaymentMethod().toString(), 
				order.getDate(), order.getModified(),
				order.getAddress().getAddress(),
				order.getAddress().getCity(),
				order.getAddress().getZipCode(),
				orderItemsEntity, isNew);
	}
	
}

package com.osr.order.infrastructure.repository.converter;

import java.util.List;
import java.util.stream.Collectors;

import com.osr.order.domain.order.Address;
import com.osr.order.domain.order.Order;
import com.osr.order.domain.order.Order.OrderStatus;
import com.osr.order.domain.order.Order.PaymentMethod;
import com.osr.order.infrastructure.repository.entities.OrderEntity;
import com.osr.order.infrastructure.repository.entities.OrderItemEntity;
import com.osr.order.domain.order.OrderItem;

public class OrderConverter {

	public static Order toDomain(OrderEntity orderEntity, List<OrderItemEntity> orderItems) {
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
		
		orderItems.forEach(oi -> order.addOrderItem(OrderItemConverter.toDomain(oi)));
		
		return order;
	}
	
	
	public static OrderEntity toEntity(Order order, boolean isNew, List<OrderItem> orderItems) {
		List<OrderItemEntity> orderItemsEntity = orderItems.stream()
				.map(oi -> OrderItemConverter.toEntity(oi, order.getOrderId(), isNew))
				.collect(Collectors.toList());
		
		return new OrderEntity(order.getOrderId().getId(), 
				order.getStatus().toString(),
				order.getPaymentMethod().toString(), 
				order.getDate(), order.getModified(),
				order.getAddres().getAddress(), 
				order.getAddres().getCity(), 
				order.getAddres().getZipCode(),
				orderItemsEntity, isNew);
	}
	
}

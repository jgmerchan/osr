package com.osr.order.infrastructure.repository.converter;

import com.osr.order.domain.order.Money;
import com.osr.order.domain.order.OrderId;
import com.osr.order.domain.order.OrderItem;
import com.osr.order.infrastructure.repository.entities.OrderItemEntity;

public class OrderItemConverter {

	public OrderItem toDomain(OrderItemEntity orderItemEntity) {
		return new OrderItem(orderItemEntity.getId(), 
				orderItemEntity.getProductId(), 
				orderItemEntity.getDescription(),
				new Money(
						orderItemEntity.getUnitPriceValue(), 
						orderItemEntity.getUnitPriceCurrencyCode()
						),
				orderItemEntity.getQuantity());
	}

	public OrderItemEntity toEntity(OrderItem orderItem, OrderId orderId, boolean isNew) {
		return new OrderItemEntity(orderItem.getOrderItemId().getId(), 
				orderId.getId(), 
				orderItem.getProductId(),
				orderItem.getDescription(), 
				orderItem.getUnitPrice().getValue(),
				orderItem.getUnitPrice().getCurrencyCode(), 
				orderItem.getQuantity(), 
				isNew);
	}

}

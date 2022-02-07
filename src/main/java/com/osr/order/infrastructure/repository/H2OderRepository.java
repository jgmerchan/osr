package com.osr.order.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.osr.order.domain.order.Address;
import com.osr.order.domain.order.Money;
import com.osr.order.domain.order.Order;
import com.osr.order.domain.order.Order.OrderStatus;
import com.osr.order.domain.order.Order.PaymentMethod;
import com.osr.order.domain.order.OrderId;
import com.osr.order.domain.order.OrderItem;
import com.osr.order.domain.order.repository.OrderRepository;

@Component
public class H2OderRepository implements OrderRepository {

	private final SpringDataH2DbOrderRepository orderrepository;

	public H2OderRepository(SpringDataH2DbOrderRepository orderrepository) {
		this.orderrepository = orderrepository;
	}

	@Override
	public Optional<Order> findById(OrderId id) {
		Optional<OrderEntity> orderEntity = orderrepository.findById(id.getId());

		if (orderEntity.isPresent()) {
			Order order = new Order(orderEntity.get().getId(), OrderStatus.valueOf(orderEntity.get().getStatus()),
					PaymentMethod.valueOf(orderEntity.get().getPaymentMethod()), orderEntity.get().getDate(),
					orderEntity.get().getModified(), new Address(orderEntity.get().getAddress(),
							orderEntity.get().getCity(), orderEntity.get().getZipCode()));

			orderEntity.get().getOrderItems().forEach(orderItemEntity -> {
				Money money = new Money(orderItemEntity.getUnitPriceValue(),
						orderItemEntity.getUnitPriceCurrencyCode());

				OrderItem orderItem = new OrderItem(orderItemEntity.getId(), orderItemEntity.getProductId(),
						orderItemEntity.getDescription(), money, orderItemEntity.getQuantity());

				order.addOrderItem(orderItem);
			});

			return Optional.of(order);
		}

		return Optional.of(null);
	}

	@Override
	public void save(Order order) {
		List<OrderItemEntity> orderItems = order.getOrderItems().stream()
				.map(orderItem -> new OrderItemEntity(orderItem.getOrderItemId().getId(), order.getOrderId().getId(),
						orderItem.getProductId(), orderItem.getDescription(), orderItem.getUnitPrice().getValue(),
						orderItem.getUnitPrice().getCurrencyCode(), orderItem.getQuantity(), true))
				.collect(Collectors.toList());

		OrderEntity orderEntity = new OrderEntity(order.getOrderId().getId(), order.getStatus().toString(),
				order.getPaymentMethod().toString(), order.getDate(), order.getModified(),
				order.getAddres().getAddress(), order.getAddres().getCity(), order.getAddres().getZipCode(),
				orderItems, true);

		orderrepository.save(orderEntity);
	}

	@Override
	public void update(Order order) {
		List<OrderItemEntity> orderItems = order.getOrderItems().stream()
				.map(orderItem -> new OrderItemEntity(orderItem.getOrderItemId().getId(), order.getOrderId().getId(),
						orderItem.getProductId(), orderItem.getDescription(), orderItem.getUnitPrice().getValue(),
						orderItem.getUnitPrice().getCurrencyCode(), orderItem.getQuantity(), false))
				.collect(Collectors.toList());

		OrderEntity orderEntity = new OrderEntity(order.getOrderId().getId(), order.getStatus().toString(),
				order.getPaymentMethod().toString(), order.getDate(), order.getModified(),
				order.getAddres().getAddress(), order.getAddres().getCity(), order.getAddres().getZipCode(),
				orderItems, false);

		orderrepository.save(orderEntity);
	}

}

package com.osr.order.application.usescases.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.osr.order.domain.order.Address;
import com.osr.order.domain.order.Money;
import com.osr.order.domain.order.Order;
import com.osr.order.domain.order.OrderItem;
import com.osr.order.domain.order.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
public class UpdateOrderStatusUseCaseTest {

	@Mock
	private OrderRepository orderRepository;
	
	@Mock
	private GetOrderUseCase getOrderUseCase;
	
	private UpdateOrderStatusUseCase updateOrderStatusUseCase;
	
	@BeforeEach
	public void setUp() {
		updateOrderStatusUseCase = new UpdateOrderStatusUseCase(orderRepository, getOrderUseCase);
	}
	
	@Test
	public void whenUpdateStatusOrderShouldHaveNextStatusInTheSequence() {
		Order order = createOrder();
		
		when(getOrderUseCase.getOrderById(order.getOrderId().getId())).thenReturn(order);
		
		Order expectedOrder = updateOrderStatusUseCase.changeNextStatusOrder(order.getOrderId().getId());
		
		assertEquals(Order.OrderStatus.COOKING, expectedOrder.getStatus());
		
	}
	
	private Order createOrder() {
		Order order = new Order(Order.PaymentMethod.CASH, createAddress());
		
		createOrderItemsList().forEach(orderItem -> order.addOrderItem(orderItem));
		
		return order;
	}
	
	private Address createAddress() {
		return new Address("Av. Puerta de Hierro, s/n", "Madrid", "28071");
	}
	
	private List<OrderItem> createOrderItemsList() {
		List<OrderItem> orderItems = new ArrayList<>();
		
		orderItems.add(new OrderItem(UUID.randomUUID(), 
				"Tortilla de patata", 
				new Money(new BigDecimal(5f)), 
				1));
		orderItems.add(new OrderItem(UUID.randomUUID(), 
				"Paella", 
				new Money(new BigDecimal(11.5f)), 
				1));
		
		return orderItems;
	}
	
	@Test
	public void whenUpdateTheStatusOrderModifiedDateShouldBeAfterTahnPreviousModififedDate() {
		Order order = createOrder();
		
		Date previousModifiedDate = order.getModified();
		
		when(getOrderUseCase.getOrderById(order.getOrderId().getId())).thenReturn(order);
		
		Order savedOrder = updateOrderStatusUseCase.changeNextStatusOrder(order.getOrderId().getId());
		
		Date newModifiedDAte = savedOrder.getModified();
		
		assertTrue(newModifiedDAte.after(previousModifiedDate));
	}
	
	@Test
	public void whenCancelOrderItsStatusShouldBeCancelled() {
		Order order = createOrder();
		
		when(getOrderUseCase.getOrderById(order.getOrderId().getId())).thenReturn(order);
		
		Order orderCancelled = updateOrderStatusUseCase.cancelOrder(order.getOrderId().getId());
		
		assertEquals(Order.OrderStatus.CANCELLED, orderCancelled.getStatus());
	}
	
}

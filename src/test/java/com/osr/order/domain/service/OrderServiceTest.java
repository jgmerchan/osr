package com.osr.order.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.osr.order.domain.order.Address;
import com.osr.order.domain.order.Money;
import com.osr.order.domain.order.Order;
import com.osr.order.domain.order.OrderItem;
import com.osr.order.domain.order.repository.OrderRepository;
import com.osr.order.domain.order.service.OrderService;
import com.osr.order.domain.order.service.OrderServiceException;
import com.osr.order.domain.order.service.OrderServiceImpl;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

	private OrderService orderService;
	
	@Mock
	private OrderRepository orderRepository;
	
	@Captor
	private ArgumentCaptor<Order> orderArgumentCaptor;
	
	@BeforeEach
	public void setUp() {
		orderService = new OrderServiceImpl(orderRepository);
	}
	
	@Test
	public void createOrderWihoutOrderItemsShouldThrowsIllegalArgumentException() {
		assertThrows(OrderServiceException.class, () -> {
			orderService.createOrder(Order.PaymentMethod.CASH, createAddress(), null);
		});
	}
	
	@Test
	public void createOrderWithoutPaymentMethodShouldThrowsIllegalArgumentException() {
		assertThrows(OrderServiceException.class, () -> {
			orderService.createOrder(null, createAddress(), createOrderItemsList());
		});
	}
	
	@Test
	public void createdOrderShouldHaveHeadrStatus() {
		orderService.createOrder(Order.PaymentMethod.CASH, createAddress(), createOrderItemsList());
		
		verify(orderRepository).save(orderArgumentCaptor.capture());
		Order savedOrder = orderArgumentCaptor.getValue();
		
		assertEquals(Order.OrderStatus.HEARD, savedOrder.getStatus());
		
	}
	
	@Test
	public void createdOrdeShouldHaveTheSameCreatedAndModifiedDate() {
		orderService.createOrder(Order.PaymentMethod.CASH, createAddress(), createOrderItemsList());
		
		verify(orderRepository).save(orderArgumentCaptor.capture());
		Order savedOrder = orderArgumentCaptor.getValue();
		
		assertEquals(savedOrder.getDate(), savedOrder.getModified());
	}
	
	private Address createAddress() {
		return new Address("Av. Puerta de Hierro, s/n", "Madrid", "28071");
	}
	
	private List<OrderItem> createOrderItemsList() {
		List<OrderItem> orderItems = new ArrayList<>();
		
		orderItems.add(new OrderItem(UUID.randomUUID(), "Tortilla de patata", new Money(new BigDecimal(5f)), 1));
		orderItems.add(new OrderItem(UUID.randomUUID(), "Paella", new Money(new BigDecimal(11.5f)), 1));
		
		return orderItems;
	}
	
	@Test
	public void updateOrderStatusInHeardOrderShouldBeCooking() {
		Order order = createOrder();
		
		when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
		
		orderService.changeNextStatusOrder(order.getId());
		
		verify(orderRepository).save(orderArgumentCaptor.capture());
		Order savedOrder = orderArgumentCaptor.getValue();
		
		assertEquals(Order.OrderStatus.COOKINGN, savedOrder.getStatus());
	}
	
	@Test
	public void whenUpdateTheStatusOrderModifiedDateShouldBeAfterTahnPreviousModififedDate() {
		Order order = createOrder();
		
		Date previousModifiedDate = order.getModified();
		
		when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
		
		orderService.changeNextStatusOrder(order.getId());
		
		verify(orderRepository).save(orderArgumentCaptor.capture());
		Order savedOrder = orderArgumentCaptor.getValue();
		Date newModifiedDAte = savedOrder.getModified();
		
		assertTrue(newModifiedDAte.after(previousModifiedDate));
	}
	
	private Order createOrder() {
		Order order = new Order(Order.PaymentMethod.CASH, createAddress());
		
		createOrderItemsList().forEach(orderItem -> order.addOrderItem(orderItem));
		
		return order;
	}
	
}

package com.osr.order.domain.order.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import com.osr.order.domain.order.OrderId;
import com.osr.order.domain.order.OrderItem;
import com.osr.order.domain.order.repository.OrderRepository;

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
	public void createOrderWihoutOrderItemsShouldThrowsOrderServicetException() {
		assertThrows(OrderServiceException.class, () -> {
			orderService.createOrder(Order.PaymentMethod.CASH, createAddress(), null);
		});
	}
	
	@Test
	public void createOrderWithoutPaymentMethodShouldThrowsOrderServiceException() {
		assertThrows(OrderServiceException.class, () -> {
			orderService.createOrder(null, createAddress(), createOrderItemsList());
		});
	}
	
	@Test
	public void createdOrderShouldHaveHeadrStatus() {
		UUID orderUuid = orderService.createOrder(Order.PaymentMethod.CASH, createAddress(), createOrderItemsList());
		
		verify(orderRepository).save(orderArgumentCaptor.capture());
		Order savedOrder = orderArgumentCaptor.getValue();
		
		assertTrue(orderUuid != null);
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
		
		when(orderRepository.findById(order.getOrderId())).thenReturn(Optional.of(order));
		
		Order savedOrder = orderService.changeNextStatusOrder(order.getOrderId().getId());
		
		assertEquals(Order.OrderStatus.COOKING, savedOrder.getStatus());
	}
	
	@Test
	public void whenUpdateTheStatusOrderModifiedDateShouldBeAfterTahnPreviousModififedDate() {
		Order order = createOrder();
		
		Date previousModifiedDate = order.getModified();
		
		when(orderRepository.findById(order.getOrderId())).thenReturn(Optional.of(order));
		
		Order savedOrder = orderService.changeNextStatusOrder(order.getOrderId().getId());
		
		Date newModifiedDAte = savedOrder.getModified();
		
		assertTrue(newModifiedDAte.after(previousModifiedDate));
	}
	
	private Order createOrder() {
		Order order = new Order(Order.PaymentMethod.CASH, createAddress());
		
		createOrderItemsList().forEach(orderItem -> order.addOrderItem(orderItem));
		
		return order;
	}
	
	@Test
	public void whenCancelOrderItsStatusShouldBeCancelled() {
		Order order = createOrder();
		
		when(orderRepository.findById(order.getOrderId())).thenReturn(Optional.of(order));
		
		orderService.cancelOrder(order.getOrderId().getId());
		
		verify(orderRepository).update(orderArgumentCaptor.capture());
		Order savedOrder = orderArgumentCaptor.getValue();
		
		assertEquals(Order.OrderStatus.CANCELLED, savedOrder.getStatus());
	}
	
	@Test
	public void whenCancelOrderItsModifiedDateShouldBeAfterThanPreviousModififedDate() throws InterruptedException {
		Order order = createOrder();
		
		Date previousModifiedDate = order.getModified();
		
		when(orderRepository.findById(order.getOrderId())).thenReturn(Optional.of(order));
		
		Thread.sleep(100);
		
		orderService.cancelOrder(order.getOrderId().getId());
		
		verify(orderRepository).update(orderArgumentCaptor.capture());
		Order savedOrder = orderArgumentCaptor.getValue();
		Date newModifiedDAte = savedOrder.getModified();
		
		assertTrue(newModifiedDAte.after(previousModifiedDate));
	}
	
	@Test
	public void checkOrderStatusOfNullIdShouldThrowsOrderServiceException() {
		assertThrows(OrderServiceException.class, () -> {
			orderService.checkStatus(null);
		});
	}
	
	@Test
	public void whenCheckStatusIfModifiedDateIsOver5MinutesTheStatusShouldBeUpdatedToNextStatus() {
		Order order = mock(Order.class);
		
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		calendar.add(Calendar.MINUTE, -6);
		
		when(order.getOrderId()).thenReturn(new OrderId(UUID.randomUUID()));
		when(order.getStatus()).thenReturn(Order.OrderStatus.HEARD).thenReturn(Order.OrderStatus.COOKING);
		when(order.getModified()).thenReturn(calendar.getTime());
		when(orderRepository.findById(order.getOrderId())).thenReturn(Optional.of(order));
		
		Order orderChecked = orderService.checkStatus(order.getOrderId().getId());
		
		assertEquals(order.getStatus().next(), orderChecked.getStatus());
	}
	
}

package com.osr.order.application.usescases.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import com.osr.order.domain.order.OrderId;
import com.osr.order.domain.order.OrderItem;
import com.osr.order.domain.order.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
public class CheckOrderStatusUseCaseTest {

	@Mock
	private OrderRepository orderRepository;
	
	@Mock
	private GetOrderUseCase getOrderUseCase;
	
	private CheckOrderStatusUseCase checkOrderStatusUseCase;
	
	@BeforeEach
	public void setUp() {
		checkOrderStatusUseCase = new CheckOrderStatusUseCase(getOrderUseCase, orderRepository);
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
		when(getOrderUseCase.getOrderById(order.getOrderId().getId())).thenReturn(order);
		
		Order orderChecked = checkOrderStatusUseCase.checkOrderStatus(order.getOrderId().getId());
		
		assertEquals(order.getStatus().next(), orderChecked.getStatus());
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
	public void whenCheckStatusIfModifiedDateIsLessThan5MinutesTheStatusShouldBeTheSame() {
		Order order = createOrder();
		
		when(getOrderUseCase.getOrderById(order.getOrderId().getId())).thenReturn(order);
		
		Order orderChecked = checkOrderStatusUseCase.checkOrderStatus(order.getOrderId().getId());
		
		assertEquals(order.getStatus(), orderChecked.getStatus());
	}
	
	
	
}

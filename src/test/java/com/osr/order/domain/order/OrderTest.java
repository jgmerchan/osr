package com.osr.order.domain.order;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderTest {
	
	private Order order;
	
	@BeforeEach
	public void setUp() {
		order = createOrder();
	}
	
	private Order createOrder() {
		Address orderAddress = new Address("Av. Puerta de Hierro, s/n", "Madrid", "28071");
		
		Order order = new Order(Order.PaymentMethod.CASH, orderAddress);
		
		order.addOrderItem(new OrderItem(UUID.randomUUID(), "Tortilla de patata", new Money(new BigDecimal(2.3f)), 1));
		order.addOrderItem(new OrderItem(UUID.randomUUID(), "Paella", new Money(new BigDecimal(3f)), 1));
		
		return order;
	}
	
	@Test
	public void nextStatusToHeardShouldBeCooking() {
		assertEquals(Order.OrderStatus.COOKINGN, Order.OrderStatus.HEARD.next());
	}

	@Test
	public void nextStatusToCookingShouldBeOnTheWay() {
		assertEquals(Order.OrderStatus.ON_THE_WAY, Order.OrderStatus.COOKINGN.next());
	}

	@Test
	public void nextSatusToOnTheWayShouldBeEnjoyYourMeal() {
		assertEquals(Order.OrderStatus.ENJOY_YOUR_MEAL, Order.OrderStatus.ON_THE_WAY.next());
	}
	
	@Test
	public void nextStatusToEnjoyYourMealShouldBeEnjoyYourMeal() {
		assertEquals(Order.OrderStatus.ENJOY_YOUR_MEAL, Order.OrderStatus.ENJOY_YOUR_MEAL.next());
	}
	
	@Test
	public void nextStatusToCancelledShouldBeCancelled() {
		assertEquals(Order.OrderStatus.CANCELLED, Order.OrderStatus.CANCELLED.next());
	}

	@Test
	public void totalAmountShouldBe50point3() {
		assertEquals(5.3f, order.calculateTotalPrice());
	}
	
}

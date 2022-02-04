package com.osr.order.domain.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
public class Order {

	private OrderId orderId;
	private OrderStatus status;
	private PaymentMethod paymentMethod;
	private Date date;
	private Date modified;
	private Address addres;
	private List<OrderItem> orderItems;
	
	public enum OrderStatus {
		HEARD, 
		COOKING, 
		ON_THE_WAY, 
		ENJOY_YOUR_MEAL, 
		CANCELLED;

		private static OrderStatus[] values = values();

		public OrderStatus next() {
			if (this.equals(OrderStatus.ENJOY_YOUR_MEAL) || this.equals(OrderStatus.CANCELLED)) {
				return this;
			}

			return values[(ordinal() + 1) % values.length];
		}
	}
	
	public enum PaymentMethod {
		CASH,
		CREDIT_CARD,
		DEBIT_CARD,
		PAYPAL
	}


	public Order(PaymentMethod paymentMethod, Address addres) {
		this.orderId = new OrderId();
		this.status = OrderStatus.HEARD;
		this.paymentMethod = paymentMethod;
		this.date = new Date();
		this.modified = new Date();
		this.addres = addres;
		this.orderItems = new ArrayList<>();
	}
	
	public Order(UUID id, OrderStatus status, PaymentMethod paymentMethod, Date date, Date modified, Address addres) {
		this.orderId = new OrderId(id);
		this.status = status;
		this.paymentMethod = paymentMethod;
		this.date = date;
		this.modified = modified;
		this.addres = addres;
		this.orderItems = new ArrayList<>();
	}
	
	public void addOrderItem(OrderItem orderItem) {
		this.orderItems.add(new OrderItem(this.orderId, orderItem.getProductId(), orderItem.getDescription(), orderItem.getUnitPrice(), orderItem.getQuantity()));
	}

	public float calculateTotalPrice() {
		return orderItems.stream()
				.map(orderItem -> orderItem.getUnitPrice().getValue().floatValue())
				.reduce(Float::sum).get();
	}
	
	public void changeToNextStatus() {
		this.modified = new Date();
		this.status = this.status.next();
	}
	
	public void changeStatusToCancelled() {
		this.modified = new Date();
		this.status = OrderStatus.CANCELLED;
	}
	
}
package com.osr.order.application.request;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.osr.order.domain.order.Address;
import com.osr.order.domain.order.Order.PaymentMethod;
import com.osr.order.domain.order.OrderItem;

import lombok.Getter;

@Getter
public class CreateOrderRequest {

	@NotNull
	private PaymentMethod paymentMethod;

	@NotNull
	private Address addres;

	@NotEmpty
	private List<OrderItem> orderItems;

	@JsonCreator
	public CreateOrderRequest(@JsonProperty("paymentMethod") @NotNull PaymentMethod paymentMethod,
			@JsonProperty("address") @NotNull Address address,
			@JsonProperty("orderItmes") @NotEmpty List<OrderItem> orderItems) {
		this.paymentMethod = paymentMethod;
		this.addres = address;
		this.orderItems = orderItems;
	}

}

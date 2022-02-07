package com.osr.order.application.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.osr.order.application.request.CancelOrderRequest;
import com.osr.order.application.request.CheckOrderStatusRequest;
import com.osr.order.application.request.CreateOrderRequest;
import com.osr.order.application.request.GetOrderRequest;
import com.osr.order.application.request.UpdateOrderRequest;
import com.osr.order.application.response.CheckOrderStatusResponse;
import com.osr.order.application.response.CreateOrderResponse;
import com.osr.order.application.response.GetOrderResponse;
import com.osr.order.application.response.UpdateOrderResponse;
import com.osr.order.domain.order.Address;
import com.osr.order.domain.order.Money;
import com.osr.order.domain.order.Order;
import com.osr.order.domain.order.Order.PaymentMethod;
import com.osr.order.domain.order.OrderItem;
import com.osr.order.domain.order.service.OrderService;
import com.osr.order.infrastructure.rest.OrderController;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(OrderController.class)
public class OrderControllerTest {

	@MockBean
	private OrderService orderService;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private JacksonTester<CreateOrderRequest> jsonRequestCreateOrder;

	@Autowired
	private JacksonTester<CreateOrderResponse> jsonRequestCreateResponse;

	@Autowired
	private JacksonTester<UpdateOrderRequest> jsonRequestUpdateOrder;

	@Autowired
	private JacksonTester<UpdateOrderResponse> jsonOrderResponse;

	@Autowired
	private JacksonTester<CancelOrderRequest> jsonCancelOrderRequest;

	@Autowired
	private JacksonTester<CheckOrderStatusRequest> jsonCheckOrderStatusRequest;

	@Autowired
	private JacksonTester<CheckOrderStatusResponse> jsonCheckOrderStatusResponse;

	@Autowired
	private JacksonTester<GetOrderRequest> jsonGetOrderRequest;

	@Autowired
	private JacksonTester<GetOrderResponse> jsonGetOrderResponse;

	@Test
	public void createOrderWithCorrectPayLoadShoulRespond201() throws Exception {
		CreateOrderRequest orderRequest = createOrderRequest(PaymentMethod.PAYPAL);
		CreateOrderResponse orderResponse = new CreateOrderResponse(UUID.randomUUID());

		when(orderService.createOrder(orderRequest.getPaymentMethod(), orderRequest.getAddres(),
				orderRequest.getOrderItems())).thenReturn(orderResponse.getId());

		MockHttpServletResponse response = mvc.perform(post("/api/order").contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE).content(jsonRequestCreateOrder.write(orderRequest).getJson()))
				.andReturn().getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		assertEquals(jsonRequestCreateResponse.write(orderResponse).getJson(), response.getContentAsString());
	}

	private CreateOrderRequest createOrderRequest(PaymentMethod paymentMethod) {
		Address addres = new Address("Av. Puerta de Hierro, s/n", "Madrid", "28071");

		OrderItem orderItem1 = new OrderItem(UUID.randomUUID(), "Tortilla de patata", new Money(new BigDecimal(5)), 1);
		OrderItem orderItem2 = new OrderItem(UUID.randomUUID(), "Paella", new Money(new BigDecimal(6)), 1);

		return new CreateOrderRequest(paymentMethod, addres, List.of(orderItem1, orderItem2));
	}

	@Test
	public void whenCreateOrderWithoutPaymentMethodShouldResponse400() throws Exception {
		CreateOrderRequest orderRequest = createOrderRequest(null);

		MockHttpServletResponse response = mvc.perform(post("/api/order").contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE).content(jsonRequestCreateOrder.write(orderRequest).getJson()))
				.andReturn().getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}

	@Test
	public void whenUpdateOrderWithCorrectPaloadShouldRespond200() throws IOException, Exception {
		Order order = new Order(PaymentMethod.PAYPAL, new Address("Av. Puerta de Hierro, s/n", "Madrid", "28071"));

		UpdateOrderRequest updateOrderRequest = new UpdateOrderRequest(order.getOrderId().getId());

		when(orderService.changeNextStatusOrder(updateOrderRequest.getId())).thenReturn(order);

		MockHttpServletResponse response = mvc.perform(
				put("/api/order").contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE)
						.content(jsonRequestUpdateOrder.write(updateOrderRequest).getJson()))
				.andReturn().getResponse();

		UpdateOrderResponse updateOrderResponse = new UpdateOrderResponse(order.getOrderId().getId(),
				order.getStatus());

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(jsonOrderResponse.write(updateOrderResponse).getJson(), response.getContentAsString());
	}

	@Test
	public void whenCancelWithCorrectPaloadShouldRespond200() throws IOException, Exception {
		CancelOrderRequest calcelOrderRequest = new CancelOrderRequest(UUID.randomUUID());

		MockHttpServletResponse response = mvc.perform(put("/api/order/cancel")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE)
				.content(jsonCancelOrderRequest.write(calcelOrderRequest).getJson())).andReturn().getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void whenCheckStatusWithCorrectPayLoadShouldRespond200() throws IOException, Exception {
		Order order = new Order(PaymentMethod.PAYPAL, new Address("Av. Puerta de Hierro, s/n", "Madrid", "28071"));

		CheckOrderStatusRequest checkOrderStatusRequest = new CheckOrderStatusRequest(order.getOrderId().getId());
		CheckOrderStatusResponse checkOrderStatusResponse = new CheckOrderStatusResponse(order.getOrderId().getId(),
				order.getStatus());

		when(orderService.checkStatus(order.getOrderId().getId())).thenReturn(order);

		MockHttpServletResponse response = mvc
				.perform(get("/api/order/status").contentType(MediaType.APPLICATION_JSON_VALUE)
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.content(jsonCheckOrderStatusRequest.write(checkOrderStatusRequest).getJson()))
				.andReturn().getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(jsonCheckOrderStatusResponse.write(checkOrderStatusResponse).getJson(),
				response.getContentAsString());
	}

	@Test
	public void whenGetOrderWithCorrectOrderIdShouldRespond200() throws IOException, Exception {
		Order order = new Order(PaymentMethod.PAYPAL, new Address("Av. Puerta de Hierro, s/n", "Madrid", "28071"));

		GetOrderRequest getOrderRequest = new GetOrderRequest(order.getOrderId().getId());
		GetOrderResponse getOrderResponse = new GetOrderResponse(order);

		when(orderService.getOrderById(order.getOrderId().getId())).thenReturn(order);

		MockHttpServletResponse response = mvc.perform(get("/api/order").contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE).content(jsonGetOrderRequest.write(getOrderRequest).getJson()))
				.andReturn().getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(jsonGetOrderResponse.write(getOrderResponse).getJson(), response.getContentAsString());
	}

}

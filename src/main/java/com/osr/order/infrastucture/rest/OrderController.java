package com.osr.order.infrastucture.rest;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.osr.order.application.request.CancelOrderRequest;
import com.osr.order.application.request.CheckOrderStatusRequest;
import com.osr.order.application.request.CreateOrderRequest;
import com.osr.order.application.request.GetOrderRequest;
import com.osr.order.application.request.UpdateOrderRequest;
import com.osr.order.application.response.CheckOrderStatusResponse;
import com.osr.order.application.response.CreateOrderResponse;
import com.osr.order.application.response.GetOrderResponse;
import com.osr.order.application.response.UpdateOrderResponse;
import com.osr.order.domain.order.Order;
import com.osr.order.domain.order.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {

	private OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> createOrder(@RequestBody @Valid final CreateOrderRequest createOrderRequest) {
		try {
			UUID id = orderService.createOrder(createOrderRequest.getPaymentMethod(), createOrderRequest.getAddres(),
					createOrderRequest.getOrderItems());

			return new ResponseEntity<>(new CreateOrderResponse(id), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> updateOrderStatus(@RequestBody @Valid final UpdateOrderRequest updateOrderRequest) {
		try {
			Order order = orderService.changeNextStatusOrder(updateOrderRequest.getId());

			return new ResponseEntity<>(new UpdateOrderResponse(order.getOrderId().getId(), order.getStatus()),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(value = "/cancel", consumes = MediaType.APPLICATION_JSON_VALUE)
	HttpStatus cancelOrder(@RequestBody @Valid CancelOrderRequest cancelOrderRequest) {
		try {
			orderService.cancelOrder(cancelOrderRequest.getId());

			return HttpStatus.OK;
		} catch (Exception e) {
			return HttpStatus.BAD_REQUEST;
		}
	}

	@GetMapping(value = "/status", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> checkOrderStatus(@RequestBody @Valid CheckOrderStatusRequest checkOrderStatusRequest) {
		try {
			Order order = orderService.checkStatus(checkOrderStatusRequest.getId());

			return new ResponseEntity<>(new CheckOrderStatusResponse(order.getOrderId().getId(), order.getStatus()),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getOrder(@RequestBody @Valid GetOrderRequest getOrderRequest) {
		try {
			Order order = orderService.getOrderById(getOrderRequest.getId());

			return new ResponseEntity<>(new GetOrderResponse(order), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}

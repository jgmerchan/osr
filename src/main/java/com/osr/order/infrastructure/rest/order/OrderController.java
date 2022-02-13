package com.osr.order.infrastructure.rest.order;

import com.osr.order.application.usescases.order.CheckOrderStatusUseCase;
import com.osr.order.application.usescases.order.CreateOrderUseCase;
import com.osr.order.application.usescases.order.GetOrderUseCase;
import com.osr.order.application.usescases.order.UpdateOrderStatusUseCase;
import com.osr.order.application.usescases.order.exceptions.OrderIdDoesNotExistsException;
import com.osr.order.application.usescases.order.exceptions.ParameterNotFoundException;
import com.osr.order.domain.order.Order;
import com.osr.order.infrastructure.rest.order.request.CreateOrderRequest;
import com.osr.order.infrastructure.rest.order.response.CheckOrderStatusResponse;
import com.osr.order.infrastructure.rest.order.response.CreateOrderResponse;
import com.osr.order.infrastructure.rest.order.response.GetOrderResponse;
import com.osr.order.infrastructure.rest.order.response.UpdateOrderResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
public class OrderController {

	private final CreateOrderUseCase createOrderUseCase;
	private final GetOrderUseCase getOrderUseCase;
	private final UpdateOrderStatusUseCase updateOrderStatusUseCase;
	private final CheckOrderStatusUseCase checkOrderStatusUseCase;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> createOrder(@RequestBody @Valid final CreateOrderRequest createOrderRequest) {
		UUID id = createOrderUseCase.createOrder(createOrderRequest.getPaymentMethod(), createOrderRequest.getAddres(),
				createOrderRequest.getOrderItems());

		return new ResponseEntity<>(new CreateOrderResponse(id), HttpStatus.CREATED);
	}

	@PutMapping(value = "/{orderId}/status", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> updateOrderStatus(@PathVariable String orderId,
										@RequestParam(defaultValue = "false") boolean cancel) {
		Order order = cancel ?
				updateOrderStatusUseCase.cancelOrder(UUID.fromString(orderId)) :
				updateOrderStatusUseCase.changeNextStatusOrder(UUID.fromString(orderId));

		return new ResponseEntity<>(new UpdateOrderResponse(order.getOrderId().getId(), order.getStatus()),
				HttpStatus.OK);
	}

	@GetMapping(value = "/{orderId}/status", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> checkOrderStatus(@PathVariable String orderId) {
		Order order = checkOrderStatusUseCase.checkOrderStatus(UUID.fromString(orderId));

		return new ResponseEntity<>(new CheckOrderStatusResponse(order.getOrderId().getId(), order.getStatus()),
				HttpStatus.OK);
	}

	@GetMapping(value = "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getOrder(@PathVariable String orderId) {
		Order order = getOrderUseCase.getOrderById(UUID.fromString(orderId));

		return new ResponseEntity<>(new GetOrderResponse(order), HttpStatus.OK);
	}

	@ExceptionHandler({OrderIdDoesNotExistsException.class, ParameterNotFoundException.class})
	public ResponseEntity<?> handleExcepction(Exception ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

}

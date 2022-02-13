package com.osr.order.application.usescases.order;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.osr.order.application.usescases.order.exceptions.OrderIdDoesNotExistsException;

@ExtendWith(MockitoExtension.class)
public class GetOrderUseCaseTest {

	@Mock
	private FindOrderByIdUseCase findOrderByIdUseCase;
	
	private GetOrderUseCase getOrderUseCase;
	
	@BeforeEach
	public void setUp() {
		getOrderUseCase = new GetOrderUseCase(findOrderByIdUseCase);
	}
	
	@Test
	public void whenGetOrderByIdThatNotExistsShouldThrowsOrderIdDoesNotExistsException() {
		UUID orderId = UUID.randomUUID();
		
		when(findOrderByIdUseCase.findById(orderId)).thenReturn(Optional.empty());
		
		assertThrows(OrderIdDoesNotExistsException.class, () -> getOrderUseCase.getOrderById(orderId));
	}
	
}

package com.osr.order.application.usescases.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.osr.order.application.usescases.order.exceptions.ParameterNotFoundException;
import com.osr.order.domain.order.Address;
import com.osr.order.domain.order.Money;
import com.osr.order.domain.order.Order;
import com.osr.order.domain.order.OrderItem;
import com.osr.order.domain.order.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
public class CreateOrderUseCaseTest {

    @Mock
    private OrderRepository orderRepository;

    @Captor
    private ArgumentCaptor<Order> orderArgumentCaptor;

    private CreateOrderUseCase createOrderUseCase;

    @BeforeEach
    public void setUp() {
        createOrderUseCase = new CreateOrderUseCase(orderRepository);
    }

    @Test
    public void createOrderWithoutOrderItemsShouldThrowsParameterNotFoundException() {
        assertThrows(ParameterNotFoundException.class, () -> createOrderUseCase
                .createOrder(Order.PaymentMethod.CASH, createAddress(), null));
    }

    @Test
    public void createOrderWithoutPaymentMethodShouldThrowsParameterNotFoundException() {
        assertThrows(ParameterNotFoundException.class, () -> createOrderUseCase
                .createOrder(null, createAddress(), createOrderItemsList()));
    }

    @Test
    public void createdOrderShouldHaveHeardStatus() {
        UUID orderUuid = createOrderUseCase
                .createOrder(Order.PaymentMethod.CASH, createAddress(), createOrderItemsList());

        verify(orderRepository).save(orderArgumentCaptor.capture());
        Order savedOrder = orderArgumentCaptor.getValue();

        assertNotNull(orderUuid);
        assertEquals(Order.OrderStatus.HEARD, savedOrder.getStatus());
    }
    private Address createAddress() {
        return new Address("Av. Puerta de Hierro, s/n", "Madrid", "28071");
    }

    private List<OrderItem> createOrderItemsList() {
        List<OrderItem> orderItems = new ArrayList<>();

        orderItems.add(
                new OrderItem(UUID.randomUUID(),
                        "Tortilla de patata",
                        new Money(new BigDecimal(5f)),
                        1));
        orderItems.add(
                new OrderItem(UUID.randomUUID(),
                        "Paella",
                        new Money(new BigDecimal(11.5f)),
                        1));

        return orderItems;
    }

    @Test
    public void createdOrderShouldHaveTheSameCreatedAndModifiedDate() {
        createOrderUseCase
                .createOrder(Order.PaymentMethod.CASH, createAddress(), createOrderItemsList());

        verify(orderRepository).save(orderArgumentCaptor.capture());
        Order savedOrder = orderArgumentCaptor.getValue();

        assertEquals(savedOrder.getDate(), savedOrder.getModified());
    }

}

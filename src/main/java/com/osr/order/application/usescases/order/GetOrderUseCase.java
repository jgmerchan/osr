package com.osr.order.application.usescases.order;

import com.osr.order.application.usescases.order.exceptions.OrderIdDoesNotExistsException;
import com.osr.order.domain.order.Order;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class GetOrderUseCase {

    private final FindOrderByIdUseCase findByIdUseCase;

    public Order getOrderById(UUID orderId) {
        return findByIdUseCase.findById(orderId).orElseThrow(() -> new OrderIdDoesNotExistsException(orderId));
    }

}

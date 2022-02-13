package com.osr.order.application.usescases.order;

import com.osr.order.application.usescases.order.exceptions.ParameterNotFoundException;
import com.osr.order.domain.order.Order;
import com.osr.order.domain.order.OrderId;
import com.osr.order.domain.order.repository.OrderRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class FindOrderByIdUseCase {

    private final OrderRepository orderRepository;

    public Optional<Order> findById(UUID orderId) throws ParameterNotFoundException {
        if (orderIdIsNull(orderId)) {
            throw new ParameterNotFoundException("order identifier");
        }

        return orderRepository.findById(new OrderId(orderId));
    }

    private boolean orderIdIsNull(UUID orderId) {
        return Optional.ofNullable(orderId).isEmpty();
    }
}

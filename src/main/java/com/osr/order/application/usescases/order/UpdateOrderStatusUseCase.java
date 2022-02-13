package com.osr.order.application.usescases.order;

import java.util.UUID;

import com.osr.order.domain.order.Order;
import com.osr.order.domain.order.repository.OrderRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UpdateOrderStatusUseCase {

    private final OrderRepository orderRepository;
    private final GetOrderUseCase getOrderUseCase;

    public Order changeNextStatusOrder(UUID orderId) {
        Order order = getOrderUseCase.getOrderById(orderId);

        order.changeToNextStatus();

        orderRepository.update(order);

        return order;
    }

    public Order cancelOrder(UUID orderId) {
        Order order = getOrderUseCase.getOrderById(orderId);

        order.changeStatusToCancelled();

        orderRepository.update(order);

        return order;
    }

}

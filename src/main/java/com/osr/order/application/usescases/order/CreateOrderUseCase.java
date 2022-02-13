package com.osr.order.application.usescases.order;

import com.osr.order.application.usescases.order.exceptions.ParameterNotFoundException;
import com.osr.order.domain.order.Address;
import com.osr.order.domain.order.Order;
import com.osr.order.domain.order.OrderItem;
import com.osr.order.domain.order.repository.OrderRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class CreateOrderUseCase {

    private final OrderRepository orderRepository;

    public UUID createOrder(Order.PaymentMethod paymentMethod, Address addres, List<OrderItem> orderItems)
            throws ParameterNotFoundException {
        if (hasNotOrderItems(orderItems)) {
            throw new ParameterNotFoundException("order item");
        }

        if (hasNotPaymentMethod(paymentMethod)) {
            throw new ParameterNotFoundException("payment method");
        }

        Order order = new Order(paymentMethod, addres);

        orderItems.forEach(order::addOrderItem);

        orderRepository.save(order);

        return order.getOrderId().getId();
    }

    private boolean hasNotOrderItems(List<OrderItem> orderItems) {
        return orderItems == null || orderItems.isEmpty();
    }

    private boolean hasNotPaymentMethod(Order.PaymentMethod paymentMethod) {
        return paymentMethod == null;
    }

}

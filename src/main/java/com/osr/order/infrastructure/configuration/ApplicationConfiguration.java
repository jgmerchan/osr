package com.osr.order.infrastructure.configuration;

import com.osr.order.application.usescases.order.CheckOrderStatusUseCase;
import com.osr.order.application.usescases.order.CreateOrderUseCase;
import com.osr.order.application.usescases.order.FindOrderByIdUseCase;
import com.osr.order.application.usescases.order.GetOrderUseCase;
import com.osr.order.application.usescases.order.UpdateOrderStatusUseCase;
import com.osr.order.domain.order.repository.OrderRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public CreateOrderUseCase createOrderUseCase(OrderRepository orderRepository) {
        return new CreateOrderUseCase(orderRepository);
    }

    @Bean
    public FindOrderByIdUseCase findByIdUseCase(OrderRepository orderRepository) {
        return new FindOrderByIdUseCase(orderRepository);
    }

    @Bean
    public GetOrderUseCase getOrderUseCase(FindOrderByIdUseCase findByIdUseCase) {
        return new GetOrderUseCase(findByIdUseCase);
    }

    @Bean
    public UpdateOrderStatusUseCase updateOrderStatusUseCase(OrderRepository orderRepository,
                                                             GetOrderUseCase getOrderUseCase) {
        return new UpdateOrderStatusUseCase(orderRepository, getOrderUseCase);
    }
    
    @Bean
    public CheckOrderStatusUseCase checkOrderStatusUseCase(OrderRepository orderRepository,
            GetOrderUseCase getOrderUseCase) {
    	return new CheckOrderStatusUseCase(getOrderUseCase, orderRepository);
    }

}

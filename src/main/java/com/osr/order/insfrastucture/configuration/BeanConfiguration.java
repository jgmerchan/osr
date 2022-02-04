package com.osr.order.insfrastucture.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.osr.order.domain.order.repository.OrderRepository;
import com.osr.order.domain.order.service.OrderService;
import com.osr.order.domain.order.service.OrderServiceImpl;

@Configuration
public class BeanConfiguration {

	@Bean
	public OrderService orderService(final OrderRepository orderRepository) {
		return new OrderServiceImpl(orderRepository);
	}
	
}

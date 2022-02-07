package com.osr.order.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.osr.order.domain.order.repository.OrderRepository;
import com.osr.order.domain.order.service.OrderService;
import com.osr.order.domain.order.service.OrderServiceImpl;
import com.osr.order.infrastructure.repository.H2OderRepository;
import com.osr.order.infrastructure.repository.SpringDataH2DbOrderRepository;

@Configuration
public class BeanConfiguration {

	@Bean
	public OrderService orderService(final OrderRepository orderRepository) {
		return new OrderServiceImpl(orderRepository);
	}

	@Bean
	public H2OderRepository h2OrderRepositoy(SpringDataH2DbOrderRepository springDataRepository) {
		return new H2OderRepository(springDataRepository);
	}

}

package com.osr.order.infrastructure.configuration;

import com.osr.order.infrastructure.repository.H2OderRepository;
import com.osr.order.infrastructure.repository.SpringDataH2DbOrderRepository;
import com.osr.order.infrastructure.repository.converter.OrderConverter;
import com.osr.order.infrastructure.repository.converter.OrderItemConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

	@Bean
	public H2OderRepository h2OrderRepositoy(SpringDataH2DbOrderRepository springDataRepository,
											 OrderConverter orderConverter) {
		return new H2OderRepository(springDataRepository, orderConverter);
	}

	@Bean
	public OrderItemConverter orderItemConverter() {
		return new OrderItemConverter();
	}

	@Bean
	public OrderConverter orderConverter(OrderItemConverter orderItemConverter) {
		return new OrderConverter(orderItemConverter);
	}

}

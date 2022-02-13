package com.osr.order.application.usescases.order;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.osr.order.application.usescases.order.exceptions.ParameterNotFoundException;
import com.osr.order.domain.order.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
public class FindByIdUseCaseTest {

    @Mock
    private OrderRepository orderRepository;

    private FindOrderByIdUseCase findOrderByIdUseCase;

    @BeforeEach
    public void setUp() {
        findOrderByIdUseCase = new FindOrderByIdUseCase(orderRepository);
    }

    @Test
    public void whenFindOrderByIdWithoutIdShouldThrowsParameterNotFoundException() {
        assertThrows(ParameterNotFoundException.class, () -> findOrderByIdUseCase.findById(null));
    }

}

package com.osr.order.application.usescases.order.exceptions;

import java.util.UUID;

public class OrderIdDoesNotExistsException extends RuntimeException {

    /**
	 * Serialization identifier
	 */
	private static final long serialVersionUID = 8168567383764541930L;

	public OrderIdDoesNotExistsException(UUID orderId) {
        super(String.format("There Order with id %s", orderId.toString()));
    }
}

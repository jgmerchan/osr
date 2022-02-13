package com.osr.order.application.usescases.order.exceptions;

public class ParameterNotFoundException extends RuntimeException{

	/**
	 * Serialization identifier
	 */
	private static final long serialVersionUID = -6284802851834495789L;

	public ParameterNotFoundException(String param) {
        super(String.format("The %s must be informed", param));
    }
}

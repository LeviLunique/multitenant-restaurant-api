package com.restauranthub.multitenant_restaurant_api.core.exception;

public abstract class SystemBaseException extends RuntimeException {

	private final String code;

	protected SystemBaseException(String code, String message) {
		super(message);
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}

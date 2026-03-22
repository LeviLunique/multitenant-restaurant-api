package com.restauranthub.multitenant_restaurant_api.core.exception;

public class BusinessException extends SystemBaseException {

	public BusinessException(String code, String message) {
		super(code, message);
	}
}

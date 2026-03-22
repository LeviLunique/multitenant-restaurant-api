package com.restauranthub.multitenant_restaurant_api.core.exception;

public class ResourceNotFoundException extends SystemBaseException {

	public ResourceNotFoundException(String code, String message) {
		super(code, message);
	}
}

package com.restauranthub.multitenant_restaurant_api.core.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CoreExceptionsTest {

	@Test
	void shouldExposeBusinessExceptionCodeAndMessage() {
		var exception = new BusinessException("BUSINESS_ERROR", "Business rule violated.");

		assertEquals("BUSINESS_ERROR", exception.getCode());
		assertEquals("Business rule violated.", exception.getMessage());
	}

	@Test
	void shouldExposeInfrastructureExceptionCodeAndMessage() {
		var exception = new InfrastructureException("INFRA_ERROR", "Infrastructure failure.");

		assertEquals("INFRA_ERROR", exception.getCode());
		assertEquals("Infrastructure failure.", exception.getMessage());
	}

	@Test
	void shouldExposeResourceNotFoundExceptionCodeAndMessage() {
		var exception = new ResourceNotFoundException("NOT_FOUND", "Resource not found.");

		assertEquals("NOT_FOUND", exception.getCode());
		assertEquals("Resource not found.", exception.getMessage());
	}
}

package com.restauranthub.multitenant_restaurant_api.infra.web.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.restauranthub.multitenant_restaurant_api.core.exception.BusinessException;
import com.restauranthub.multitenant_restaurant_api.core.exception.InfrastructureException;
import com.restauranthub.multitenant_restaurant_api.core.exception.ResourceNotFoundException;

class RestExceptionHandlerTest {

	private final RestExceptionHandler handler = new RestExceptionHandler();

	@Test
	void shouldTranslateBusinessException() {
		var response = handler.handleBusinessException(new BusinessException("BUSINESS_ERROR", "Business rule violated."));

		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("BUSINESS_ERROR", response.getBody().code());
		assertEquals("Business rule violated.", response.getBody().message());
		assertNotNull(response.getBody().timestamp());
	}

	@Test
	void shouldTranslateResourceNotFoundException() {
		var response = handler.handleResourceNotFound(new ResourceNotFoundException("NOT_FOUND", "Resource not found."));

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("NOT_FOUND", response.getBody().code());
		assertEquals("Resource not found.", response.getBody().message());
	}

	@Test
	void shouldTranslateInfrastructureException() {
		var response = handler.handleInfrastructureException(new InfrastructureException("INFRA_ERROR", "Infrastructure failure."));

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("INFRA_ERROR", response.getBody().code());
		assertEquals("Infrastructure failure.", response.getBody().message());
	}

	@Test
	void shouldTranslateValidationException() {
		var response = handler.handleValidationException((MethodArgumentNotValidException) null);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("VALIDATION_ERROR", response.getBody().code());
		assertEquals("Request validation failed.", response.getBody().message());
	}

	@Test
	void shouldTranslateUnexpectedException() {
		var response = handler.handleUnexpectedException(new RuntimeException("boom"));

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("UNEXPECTED_ERROR", response.getBody().code());
		assertEquals("Unexpected application error.", response.getBody().message());
	}
}

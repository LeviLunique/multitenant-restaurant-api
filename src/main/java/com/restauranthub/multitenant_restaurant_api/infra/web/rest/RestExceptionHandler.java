package com.restauranthub.multitenant_restaurant_api.infra.web.rest;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.restauranthub.multitenant_restaurant_api.core.exception.BusinessException;
import com.restauranthub.multitenant_restaurant_api.core.exception.InfrastructureException;
import com.restauranthub.multitenant_restaurant_api.core.exception.ResourceNotFoundException;
import com.restauranthub.multitenant_restaurant_api.infra.web.json.ApiErrorJson;

@RestControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ApiErrorJson> handleBusinessException(BusinessException exception) {
		return buildResponse(HttpStatus.UNPROCESSABLE_ENTITY, exception.getCode(), exception.getMessage());
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiErrorJson> handleResourceNotFound(ResourceNotFoundException exception) {
		return buildResponse(HttpStatus.NOT_FOUND, exception.getCode(), exception.getMessage());
	}

	@ExceptionHandler(InfrastructureException.class)
	public ResponseEntity<ApiErrorJson> handleInfrastructureException(InfrastructureException exception) {
		return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getCode(), exception.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiErrorJson> handleValidationException(MethodArgumentNotValidException exception) {
		return buildResponse(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", "Request validation failed.");
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiErrorJson> handleUnexpectedException(Exception exception) {
		return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "UNEXPECTED_ERROR", "Unexpected application error.");
	}

	private ResponseEntity<ApiErrorJson> buildResponse(HttpStatus status, String code, String message) {
		return ResponseEntity.status(status)
				.body(new ApiErrorJson(code, message, Instant.now()));
	}
}

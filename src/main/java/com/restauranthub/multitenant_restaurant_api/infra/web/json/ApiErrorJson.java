package com.restauranthub.multitenant_restaurant_api.infra.web.json;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ApiError", description = "Standard API error response.")
public record ApiErrorJson(
		@Schema(description = "Stable application error code.", example = "VALIDATION_ERROR")
		String code,
		@Schema(description = "Human-readable error message.", example = "Request validation failed.")
		String message,
		@Schema(description = "Error timestamp in UTC.", example = "2026-03-22T22:00:00Z")
		Instant timestamp) {
}

package com.restauranthub.multitenant_restaurant_api.infra.web.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;

import org.junit.jupiter.api.Test;

class ApiErrorJsonTest {

	@Test
	void shouldExposeRecordFields() {
		var timestamp = Instant.parse("2026-03-22T12:00:00Z");
		var error = new ApiErrorJson("ERROR_CODE", "Error message", timestamp);

		assertEquals("ERROR_CODE", error.code());
		assertEquals("Error message", error.message());
		assertEquals(timestamp, error.timestamp());
	}
}

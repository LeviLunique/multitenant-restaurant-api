package com.restauranthub.multitenant_restaurant_api.infra.web.json;

import java.time.Instant;

public record ApiErrorJson(
		String code,
		String message,
		Instant timestamp) {
}

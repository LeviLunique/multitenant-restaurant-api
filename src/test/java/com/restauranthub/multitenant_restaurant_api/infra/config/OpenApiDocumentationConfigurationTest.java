package com.restauranthub.multitenant_restaurant_api.infra.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class OpenApiDocumentationConfigurationTest {

	private static final String API_TITLE = "Restaurant Hub API";
	private static final String API_VERSION = "v1";
	private static final String BAD_REQUEST_RESPONSE = "BadRequestResponse";
	private static final String API_ERROR_SCHEMA = "ApiError";

	private final OpenApiDocumentationConfiguration configuration = new OpenApiDocumentationConfiguration();

	@Test
	void shouldCreateOpenApiDocumentationWithExpectedMetadataAndComponents() {
		var openApi = configuration.restaurantHubOpenApi();

		assertNotNull(openApi);
		assertNotNull(openApi.getInfo());
		assertEquals(API_TITLE, openApi.getInfo().getTitle());
		assertEquals(API_VERSION, openApi.getInfo().getVersion());
		assertNotNull(openApi.getComponents());
		assertNotNull(openApi.getComponents().getSchemas().get(API_ERROR_SCHEMA));
		assertNotNull(openApi.getComponents().getResponses().get(BAD_REQUEST_RESPONSE));
		assertEquals("Standard error response.", openApi.getComponents().getResponses().get(BAD_REQUEST_RESPONSE).getDescription());
	}
}

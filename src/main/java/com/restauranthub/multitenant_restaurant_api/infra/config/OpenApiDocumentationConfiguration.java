package com.restauranthub.multitenant_restaurant_api.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.responses.ApiResponse;

@Configuration
public class OpenApiDocumentationConfiguration {

	private static final String API_TITLE = "Restaurant Hub API";
	private static final String API_VERSION = "v1";
	private static final String API_DESCRIPTION = "REST API for users, user types, restaurants and menu items using Clean Architecture.";
	private static final String CONTACT_NAME = "Levi Lunique";
	private static final String CONTACT_URL = "https://github.com/LeviLunique";
	private static final String CONTACT_EMAIL = "levilunique@users.noreply.github.com";
	private static final String ERROR_RESPONSE_DESCRIPTION = "Standard error response.";

	@Bean
	public OpenAPI restaurantHubOpenApi() {
		return new OpenAPI()
				.info(new Info()
						.title(API_TITLE)
						.version(API_VERSION)
						.description(API_DESCRIPTION)
						.contact(new Contact()
								.name(CONTACT_NAME)
								.url(CONTACT_URL)
								.email(CONTACT_EMAIL)))
				.components(new Components()
						.addSchemas("ApiError", new ObjectSchema()
								.addProperty("code", new StringSchema())
								.addProperty("message", new StringSchema())
								.addProperty("timestamp", new StringSchema()))
						.addResponses("BadRequestResponse", errorResponse())
						.addResponses("NotFoundResponse", errorResponse())
						.addResponses("UnprocessableEntityResponse", errorResponse())
						.addResponses("InternalServerErrorResponse", errorResponse()));
	}

	private ApiResponse errorResponse() {
		return new ApiResponse().description(ERROR_RESPONSE_DESCRIPTION);
	}
}

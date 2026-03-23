package com.restauranthub.multitenant_restaurant_api.infra.web.json;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "UsuarioRequest", description = "Payload for user creation or update.")
public record UsuarioJson(
		@Schema(description = "User full name.", example = "Levi Lunique")
		@NotBlank(message = "Name is required.")
		String nome,
		@Schema(description = "User email.", example = "levi@example.com")
		@NotBlank(message = "Email is required.")
		@Email(message = "Email must be valid.")
		String email) {
}

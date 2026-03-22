package com.restauranthub.multitenant_restaurant_api.infra.web.json;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioJson(
		@NotBlank(message = "Name is required.")
		String nome,
		@NotBlank(message = "Email is required.")
		@Email(message = "Email must be valid.")
		String email) {
}

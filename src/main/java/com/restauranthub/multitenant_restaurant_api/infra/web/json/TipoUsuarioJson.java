package com.restauranthub.multitenant_restaurant_api.infra.web.json;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuarioEnum;

public record TipoUsuarioJson(
		@NotBlank(message = "User type name is required.")
		String nome,
		@NotNull(message = "User type enum is required.")
		TipoUsuarioEnum tipo) {
}

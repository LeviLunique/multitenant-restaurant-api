package com.restauranthub.multitenant_restaurant_api.infra.web.json;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RestauranteJson(
		@NotBlank(message = "Restaurant name is required.")
		String nome,
		@NotBlank(message = "Restaurant address is required.")
		String endereco,
		@NotBlank(message = "Restaurant cuisine type is required.")
		String tipoCozinha,
		@NotBlank(message = "Restaurant opening hours are required.")
		String horarioFuncionamento,
		@NotNull(message = "Restaurant owner is required.")
		Long donoUsuarioId) {
}

package com.restauranthub.multitenant_restaurant_api.infra.web.json;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RestauranteRequest", description = "Payload for creating or updating a restaurant.")
public record RestauranteJson(
		@Schema(description = "Restaurant name.", example = "Bistro Central")
		@NotBlank(message = "Restaurant name is required.")
		String nome,
		@Schema(description = "Restaurant address.", example = "Rua A, 100")
		@NotBlank(message = "Restaurant address is required.")
		String endereco,
		@Schema(description = "Restaurant cuisine type.", example = "Francesa")
		@NotBlank(message = "Restaurant cuisine type is required.")
		String tipoCozinha,
		@Schema(description = "Restaurant opening hours.", example = "09:00-22:00")
		@NotBlank(message = "Restaurant opening hours are required.")
		String horarioFuncionamento,
		@Schema(description = "Existing owner user id.", example = "1")
		@NotNull(message = "Restaurant owner is required.")
		Long donoUsuarioId) {
}

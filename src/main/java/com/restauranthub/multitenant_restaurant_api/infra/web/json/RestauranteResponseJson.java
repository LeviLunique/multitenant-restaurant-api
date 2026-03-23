package com.restauranthub.multitenant_restaurant_api.infra.web.json;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RestauranteResponse", description = "Restaurant response payload.")
public record RestauranteResponseJson(
		@Schema(description = "Restaurant identifier.", example = "1")
		Long id,
		@Schema(description = "Restaurant name.", example = "Bistro Central")
		String nome,
		@Schema(description = "Restaurant address.", example = "Rua A, 100")
		String endereco,
		@Schema(description = "Restaurant cuisine type.", example = "Francesa")
		String tipoCozinha,
		@Schema(description = "Restaurant opening hours.", example = "09:00-22:00")
		String horarioFuncionamento,
		@Schema(description = "Owner user identifier.", example = "1")
		Long donoUsuarioId) {
}

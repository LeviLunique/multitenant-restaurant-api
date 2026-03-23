package com.restauranthub.multitenant_restaurant_api.infra.web.json;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ItemCardapioRequest", description = "Payload for creating or updating a menu item.")
public record ItemCardapioJson(
		@Schema(description = "Menu item name.", example = "Risoto")
		@NotBlank(message = "Menu item name is required.")
		String nome,
		@Schema(description = "Menu item description.", example = "Risoto de cogumelos")
		@NotBlank(message = "Menu item description is required.")
		String descricao,
		@Schema(description = "Menu item price.", example = "49.90")
		@NotNull(message = "Menu item price is required.")
		@DecimalMin(value = "0.01", message = "Menu item price must be greater than zero.")
		BigDecimal preco,
		@Schema(description = "Whether the item can only be consumed at the restaurant.", example = "true")
		@NotNull(message = "Menu item local consumption availability is required.")
		Boolean apenasConsumoNoLocal,
		@Schema(description = "Photo storage path.", example = "/fotos/risoto.jpg")
		@NotBlank(message = "Menu item photo path is required.")
		String caminhoFoto) {
}

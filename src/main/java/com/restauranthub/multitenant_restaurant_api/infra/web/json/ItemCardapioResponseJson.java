package com.restauranthub.multitenant_restaurant_api.infra.web.json;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ItemCardapioResponse", description = "Menu item response payload.")
public record ItemCardapioResponseJson(
		@Schema(description = "Menu item identifier.", example = "1")
		Long id,
		@Schema(description = "Parent restaurant identifier.", example = "1")
		Long restauranteId,
		@Schema(description = "Menu item name.", example = "Risoto")
		String nome,
		@Schema(description = "Menu item description.", example = "Risoto de cogumelos")
		String descricao,
		@Schema(description = "Menu item price.", example = "49.90")
		BigDecimal preco,
		@Schema(description = "Whether the item can only be consumed at the restaurant.", example = "true")
		Boolean apenasConsumoNoLocal,
		@Schema(description = "Photo storage path.", example = "/fotos/risoto.jpg")
		String caminhoFoto) {
}

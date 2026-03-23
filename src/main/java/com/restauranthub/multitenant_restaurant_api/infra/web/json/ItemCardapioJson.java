package com.restauranthub.multitenant_restaurant_api.infra.web.json;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ItemCardapioJson(
		@NotBlank(message = "Menu item name is required.")
		String nome,
		@NotBlank(message = "Menu item description is required.")
		String descricao,
		@NotNull(message = "Menu item price is required.")
		@DecimalMin(value = "0.01", message = "Menu item price must be greater than zero.")
		BigDecimal preco,
		@NotNull(message = "Menu item local consumption availability is required.")
		Boolean apenasConsumoNoLocal,
		@NotBlank(message = "Menu item photo path is required.")
		String caminhoFoto) {
}

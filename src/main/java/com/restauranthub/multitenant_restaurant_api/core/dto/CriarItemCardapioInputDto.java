package com.restauranthub.multitenant_restaurant_api.core.dto;

import java.math.BigDecimal;

public record CriarItemCardapioInputDto(
		String nome,
		String descricao,
		BigDecimal preco,
		Boolean apenasConsumoNoLocal,
		String caminhoFoto) {
}

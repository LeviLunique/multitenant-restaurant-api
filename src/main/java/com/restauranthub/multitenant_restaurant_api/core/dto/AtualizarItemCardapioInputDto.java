package com.restauranthub.multitenant_restaurant_api.core.dto;

import java.math.BigDecimal;

public record AtualizarItemCardapioInputDto(
		String nome,
		String descricao,
		BigDecimal preco,
		Boolean apenasConsumoNoLocal,
		String caminhoFoto) {
}

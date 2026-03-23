package com.restauranthub.multitenant_restaurant_api.infra.web.json;

import java.math.BigDecimal;

public record ItemCardapioResponseJson(
		Long id,
		Long restauranteId,
		String nome,
		String descricao,
		BigDecimal preco,
		Boolean apenasConsumoNoLocal,
		String caminhoFoto) {
}

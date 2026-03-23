package com.restauranthub.multitenant_restaurant_api.infra.web.json;

public record RestauranteResponseJson(
		Long id,
		String nome,
		String endereco,
		String tipoCozinha,
		String horarioFuncionamento,
		Long donoUsuarioId) {
}

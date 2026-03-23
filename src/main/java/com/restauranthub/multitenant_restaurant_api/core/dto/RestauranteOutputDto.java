package com.restauranthub.multitenant_restaurant_api.core.dto;

public record RestauranteOutputDto(
		Long id,
		String nome,
		String endereco,
		String tipoCozinha,
		String horarioFuncionamento,
		Long donoUsuarioId) {
}

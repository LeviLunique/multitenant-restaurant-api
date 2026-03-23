package com.restauranthub.multitenant_restaurant_api.core.dto;

import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuarioEnum;

public record AtualizarTipoUsuarioInputDto(
		String nome,
		TipoUsuarioEnum tipo) {
}

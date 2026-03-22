package com.restauranthub.multitenant_restaurant_api.core.dto;

import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuarioEnum;

public record TipoUsuarioOutputDto(
		Long id,
		String nome,
		TipoUsuarioEnum tipo) {
}

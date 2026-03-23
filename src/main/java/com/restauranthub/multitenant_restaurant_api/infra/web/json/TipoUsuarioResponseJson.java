package com.restauranthub.multitenant_restaurant_api.infra.web.json;

import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuarioEnum;

public record TipoUsuarioResponseJson(
		Long id,
		String nome,
		TipoUsuarioEnum tipo) {
}

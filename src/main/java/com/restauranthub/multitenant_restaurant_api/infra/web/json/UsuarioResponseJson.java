package com.restauranthub.multitenant_restaurant_api.infra.web.json;

import java.util.List;

public record UsuarioResponseJson(
		Long id,
		String nome,
		String email,
		List<TipoUsuarioResponseJson> tiposUsuario) {
}

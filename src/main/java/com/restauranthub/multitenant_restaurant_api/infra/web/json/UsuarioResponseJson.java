package com.restauranthub.multitenant_restaurant_api.infra.web.json;

public record UsuarioResponseJson(
		Long id,
		String nome,
		String email) {
}

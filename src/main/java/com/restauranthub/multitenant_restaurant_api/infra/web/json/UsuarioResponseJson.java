package com.restauranthub.multitenant_restaurant_api.infra.web.json;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UsuarioResponse", description = "User returned by the API.")
public record UsuarioResponseJson(
		@Schema(description = "User identifier.", example = "1")
		Long id,
		@Schema(description = "User full name.", example = "Levi Lunique")
		String nome,
		@Schema(description = "User email.", example = "levi@example.com")
		String email,
		@Schema(description = "User types associated with the user.")
		List<TipoUsuarioResponseJson> tiposUsuario) {
}

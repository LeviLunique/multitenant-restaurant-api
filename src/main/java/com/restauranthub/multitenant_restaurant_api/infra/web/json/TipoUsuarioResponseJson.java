package com.restauranthub.multitenant_restaurant_api.infra.web.json;

import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuarioEnum;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "TipoUsuarioResponse", description = "User type returned by the API.")
public record TipoUsuarioResponseJson(
		@Schema(description = "User type identifier.", example = "1")
		Long id,
		@Schema(description = "Descriptive user type name.", example = "Dono do restaurante")
		String nome,
		@Schema(description = "Business enum for the user type.", example = "DONO")
		TipoUsuarioEnum tipo) {
}

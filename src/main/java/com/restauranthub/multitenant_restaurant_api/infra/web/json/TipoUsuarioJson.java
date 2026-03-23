package com.restauranthub.multitenant_restaurant_api.infra.web.json;

import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuarioEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "TipoUsuarioRequest", description = "Payload for user type creation or update.")
public record TipoUsuarioJson(
		@Schema(description = "Descriptive name for the user type.", example = "Dono do restaurante")
		@NotBlank(message = "User type name is required.")
		String nome,
		@Schema(description = "Fixed business enum for the user type.", example = "DONO")
		@NotNull(message = "User type enum is required.")
		TipoUsuarioEnum tipo) {
}

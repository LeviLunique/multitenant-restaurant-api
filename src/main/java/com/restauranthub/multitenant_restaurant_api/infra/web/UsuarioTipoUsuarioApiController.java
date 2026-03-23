package com.restauranthub.multitenant_restaurant_api.infra.web;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restauranthub.multitenant_restaurant_api.core.controller.TipoUsuarioController;
import com.restauranthub.multitenant_restaurant_api.core.dto.UsuarioOutputDto;
import com.restauranthub.multitenant_restaurant_api.infra.web.json.TipoUsuarioResponseJson;
import com.restauranthub.multitenant_restaurant_api.infra.web.json.UsuarioResponseJson;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Operations for user management.")
public class UsuarioTipoUsuarioApiController {

	private final TipoUsuarioController tipoUsuarioController;

	@PostMapping("/usuarios/{usuarioId}/tipos-usuario/{tipoUsuarioId}")
	@Operation(summary = "Associate user type to user", description = "Associates an existing user type to an existing user.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Association completed successfully."),
			@ApiResponse(responseCode = "404", description = "User or user type not found.", ref = "#/components/responses/NotFoundResponse"),
			@ApiResponse(responseCode = "422", description = "Business rule violation.", ref = "#/components/responses/UnprocessableEntityResponse") })
	public UsuarioResponseJson associarAoUsuario(@PathVariable Long usuarioId, @PathVariable Long tipoUsuarioId) {
		return map(tipoUsuarioController.associarAoUsuario(usuarioId, tipoUsuarioId));
	}

	private UsuarioResponseJson map(UsuarioOutputDto usuarioOutputDto) {
		return new UsuarioResponseJson(
				usuarioOutputDto.id(),
				usuarioOutputDto.nome(),
				usuarioOutputDto.email(),
				usuarioOutputDto.tiposUsuario().stream()
						.map(tipoUsuario -> new TipoUsuarioResponseJson(tipoUsuario.id(), tipoUsuario.nome(), tipoUsuario.tipo()))
						.toList());
	}
}

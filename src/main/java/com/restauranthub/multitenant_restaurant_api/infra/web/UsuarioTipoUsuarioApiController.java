package com.restauranthub.multitenant_restaurant_api.infra.web;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restauranthub.multitenant_restaurant_api.core.controller.TipoUsuarioController;
import com.restauranthub.multitenant_restaurant_api.core.dto.UsuarioOutputDto;
import com.restauranthub.multitenant_restaurant_api.infra.web.json.TipoUsuarioResponseJson;
import com.restauranthub.multitenant_restaurant_api.infra.web.json.UsuarioResponseJson;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UsuarioTipoUsuarioApiController {

	private final TipoUsuarioController tipoUsuarioController;

	@PostMapping("/usuarios/{usuarioId}/tipos-usuario/{tipoUsuarioId}")
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

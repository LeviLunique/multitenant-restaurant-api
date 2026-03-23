package com.restauranthub.multitenant_restaurant_api.core.usecase;

import com.restauranthub.multitenant_restaurant_api.core.domain.Usuario;
import com.restauranthub.multitenant_restaurant_api.core.exception.ResourceNotFoundException;
import com.restauranthub.multitenant_restaurant_api.core.gateway.TipoUsuarioGateway;
import com.restauranthub.multitenant_restaurant_api.core.gateway.UsuarioGateway;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AssociarTipoUsuarioAoUsuarioUsecaseImpl implements AssociarTipoUsuarioAoUsuarioUsecase {

	private static final String USER_NOT_FOUND_CODE = "USER_NOT_FOUND";
	private static final String USER_NOT_FOUND_MESSAGE = "User was not found.";
	private static final String USER_TYPE_NOT_FOUND_CODE = "USER_TYPE_NOT_FOUND";
	private static final String USER_TYPE_NOT_FOUND_MESSAGE = "User type was not found.";

	private final UsuarioGateway usuarioGateway;
	private final TipoUsuarioGateway tipoUsuarioGateway;

	@Override
	public Usuario associar(Long usuarioId, Long tipoUsuarioId) {
		var usuario = usuarioGateway.obterPorId(usuarioId)
				.orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_CODE, USER_NOT_FOUND_MESSAGE));
		var tipoUsuario = tipoUsuarioGateway.obterPorId(tipoUsuarioId)
				.orElseThrow(() -> new ResourceNotFoundException(USER_TYPE_NOT_FOUND_CODE, USER_TYPE_NOT_FOUND_MESSAGE));

		usuario.associarTipoUsuario(tipoUsuario);
		return usuarioGateway.atualizar(usuario);
	}
}

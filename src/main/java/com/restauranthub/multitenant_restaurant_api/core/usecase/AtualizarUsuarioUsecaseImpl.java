package com.restauranthub.multitenant_restaurant_api.core.usecase;

import com.restauranthub.multitenant_restaurant_api.core.domain.Usuario;
import com.restauranthub.multitenant_restaurant_api.core.exception.BusinessException;
import com.restauranthub.multitenant_restaurant_api.core.exception.ResourceNotFoundException;
import com.restauranthub.multitenant_restaurant_api.core.gateway.UsuarioGateway;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AtualizarUsuarioUsecaseImpl implements AtualizarUsuarioUsecase {

	private static final String USER_NOT_FOUND_CODE = "USER_NOT_FOUND";
	private static final String USER_NOT_FOUND_MESSAGE = "User not found.";
	private static final String USER_EMAIL_ALREADY_EXISTS_CODE = "USER_EMAIL_ALREADY_EXISTS";
	private static final String USER_EMAIL_ALREADY_EXISTS_MESSAGE = "A user with this email already exists.";

	private final UsuarioGateway usuarioGateway;

	@Override
	public Usuario atualizar(Usuario usuario) {
		usuarioGateway.obterPorId(usuario.getId())
				.orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_CODE, USER_NOT_FOUND_MESSAGE));

		var usuarioExistentePorEmail = usuarioGateway.obterPorEmail(usuario.getEmail());
		if (usuarioExistentePorEmail.isPresent() && !usuarioExistentePorEmail.get().getId().equals(usuario.getId())) {
			throw new BusinessException(USER_EMAIL_ALREADY_EXISTS_CODE, USER_EMAIL_ALREADY_EXISTS_MESSAGE);
		}

		return usuarioGateway.atualizar(usuario);
	}
}

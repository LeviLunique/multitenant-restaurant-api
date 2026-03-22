package com.restauranthub.multitenant_restaurant_api.core.usecase;

import com.restauranthub.multitenant_restaurant_api.core.domain.Usuario;
import com.restauranthub.multitenant_restaurant_api.core.exception.BusinessException;
import com.restauranthub.multitenant_restaurant_api.core.gateway.UsuarioGateway;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CriarUsuarioUsecaseImpl implements CriarUsuarioUsecase {

	private static final String USER_EMAIL_ALREADY_EXISTS_CODE = "USER_EMAIL_ALREADY_EXISTS";
	private static final String USER_EMAIL_ALREADY_EXISTS_MESSAGE = "A user with this email already exists.";

	private final UsuarioGateway usuarioGateway;

	@Override
	public Long criar(Usuario usuario) {
		if (usuarioGateway.obterPorEmail(usuario.getEmail()).isPresent()) {
			throw new BusinessException(USER_EMAIL_ALREADY_EXISTS_CODE, USER_EMAIL_ALREADY_EXISTS_MESSAGE);
		}
		return usuarioGateway.criar(usuario);
	}
}

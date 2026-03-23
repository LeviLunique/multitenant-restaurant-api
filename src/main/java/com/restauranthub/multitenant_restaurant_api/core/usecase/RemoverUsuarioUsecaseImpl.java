package com.restauranthub.multitenant_restaurant_api.core.usecase;

import com.restauranthub.multitenant_restaurant_api.core.exception.ResourceNotFoundException;
import com.restauranthub.multitenant_restaurant_api.core.gateway.UsuarioGateway;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RemoverUsuarioUsecaseImpl implements RemoverUsuarioUsecase {

	private static final String USER_NOT_FOUND_CODE = "USER_NOT_FOUND";
	private static final String USER_NOT_FOUND_MESSAGE = "User not found.";

	private final UsuarioGateway usuarioGateway;

	@Override
	public void remover(Long id) {
		usuarioGateway.obterPorId(id)
				.orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_CODE, USER_NOT_FOUND_MESSAGE));
		usuarioGateway.remover(id);
	}
}

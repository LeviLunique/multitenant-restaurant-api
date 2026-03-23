package com.restauranthub.multitenant_restaurant_api.core.usecase;

import com.restauranthub.multitenant_restaurant_api.core.exception.ResourceNotFoundException;
import com.restauranthub.multitenant_restaurant_api.core.gateway.TipoUsuarioGateway;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RemoverTipoUsuarioUsecaseImpl implements RemoverTipoUsuarioUsecase {

	private static final String USER_TYPE_NOT_FOUND_CODE = "USER_TYPE_NOT_FOUND";
	private static final String USER_TYPE_NOT_FOUND_MESSAGE = "User type was not found.";

	private final TipoUsuarioGateway tipoUsuarioGateway;

	@Override
	public void remover(Long id) {
		tipoUsuarioGateway.obterPorId(id)
				.orElseThrow(() -> new ResourceNotFoundException(USER_TYPE_NOT_FOUND_CODE, USER_TYPE_NOT_FOUND_MESSAGE));
		tipoUsuarioGateway.remover(id);
	}
}

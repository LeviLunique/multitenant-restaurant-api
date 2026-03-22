package com.restauranthub.multitenant_restaurant_api.core.usecase;

import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuario;
import com.restauranthub.multitenant_restaurant_api.core.exception.BusinessException;
import com.restauranthub.multitenant_restaurant_api.core.exception.ResourceNotFoundException;
import com.restauranthub.multitenant_restaurant_api.core.gateway.TipoUsuarioGateway;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AtualizarTipoUsuarioUsecaseImpl implements AtualizarTipoUsuarioUsecase {

	private static final String USER_TYPE_NOT_FOUND_CODE = "USER_TYPE_NOT_FOUND";
	private static final String USER_TYPE_NOT_FOUND_MESSAGE = "User type was not found.";
	private static final String USER_TYPE_NAME_ALREADY_EXISTS_CODE = "USER_TYPE_NAME_ALREADY_EXISTS";
	private static final String USER_TYPE_NAME_ALREADY_EXISTS_MESSAGE = "User type name already exists.";
	private static final String USER_TYPE_ENUM_ALREADY_EXISTS_CODE = "USER_TYPE_ENUM_ALREADY_EXISTS";
	private static final String USER_TYPE_ENUM_ALREADY_EXISTS_MESSAGE = "User type enum already exists.";

	private final TipoUsuarioGateway tipoUsuarioGateway;

	@Override
	public TipoUsuario atualizar(TipoUsuario tipoUsuario) {
		tipoUsuarioGateway.obterPorId(tipoUsuario.getId())
				.orElseThrow(() -> new ResourceNotFoundException(USER_TYPE_NOT_FOUND_CODE, USER_TYPE_NOT_FOUND_MESSAGE));

		var tipoUsuarioExistente = tipoUsuarioGateway.obterPorNome(tipoUsuario.getNome());
		if (tipoUsuarioExistente.isPresent() && !tipoUsuarioExistente.get().getId().equals(tipoUsuario.getId())) {
			throw new BusinessException(USER_TYPE_NAME_ALREADY_EXISTS_CODE, USER_TYPE_NAME_ALREADY_EXISTS_MESSAGE);
		}
		var tipoUsuarioExistentePorTipo = tipoUsuarioGateway.obterPorTipo(tipoUsuario.getTipo());
		if (tipoUsuarioExistentePorTipo.isPresent()
				&& !tipoUsuarioExistentePorTipo.get().getId().equals(tipoUsuario.getId())) {
			throw new BusinessException(USER_TYPE_ENUM_ALREADY_EXISTS_CODE, USER_TYPE_ENUM_ALREADY_EXISTS_MESSAGE);
		}

		return tipoUsuarioGateway.atualizar(tipoUsuario);
	}
}

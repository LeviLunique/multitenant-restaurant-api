package com.restauranthub.multitenant_restaurant_api.core.usecase;

import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuario;
import com.restauranthub.multitenant_restaurant_api.core.exception.BusinessException;
import com.restauranthub.multitenant_restaurant_api.core.gateway.TipoUsuarioGateway;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CriarTipoUsuarioUsecaseImpl implements CriarTipoUsuarioUsecase {

	private static final String USER_TYPE_NAME_ALREADY_EXISTS_CODE = "USER_TYPE_NAME_ALREADY_EXISTS";
	private static final String USER_TYPE_NAME_ALREADY_EXISTS_MESSAGE = "User type name already exists.";
	private static final String USER_TYPE_ENUM_ALREADY_EXISTS_CODE = "USER_TYPE_ENUM_ALREADY_EXISTS";
	private static final String USER_TYPE_ENUM_ALREADY_EXISTS_MESSAGE = "User type enum already exists.";

	private final TipoUsuarioGateway tipoUsuarioGateway;

	@Override
	public Long criar(TipoUsuario tipoUsuario) {
		if (tipoUsuarioGateway.obterPorNome(tipoUsuario.getNome()).isPresent()) {
			throw new BusinessException(USER_TYPE_NAME_ALREADY_EXISTS_CODE, USER_TYPE_NAME_ALREADY_EXISTS_MESSAGE);
		}
		if (tipoUsuarioGateway.obterPorTipo(tipoUsuario.getTipo()).isPresent()) {
			throw new BusinessException(USER_TYPE_ENUM_ALREADY_EXISTS_CODE, USER_TYPE_ENUM_ALREADY_EXISTS_MESSAGE);
		}

		return tipoUsuarioGateway.criar(tipoUsuario);
	}
}

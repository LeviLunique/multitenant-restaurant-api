package com.restauranthub.multitenant_restaurant_api.core.usecase;

import java.util.List;

import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuario;
import com.restauranthub.multitenant_restaurant_api.core.gateway.TipoUsuarioGateway;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ListarTiposUsuarioUsecaseImpl implements ListarTiposUsuarioUsecase {

	private final TipoUsuarioGateway tipoUsuarioGateway;

	@Override
	public List<TipoUsuario> listar() {
		return tipoUsuarioGateway.listar();
	}
}

package com.restauranthub.multitenant_restaurant_api.core.usecase;

import java.util.List;

import com.restauranthub.multitenant_restaurant_api.core.domain.Usuario;
import com.restauranthub.multitenant_restaurant_api.core.gateway.UsuarioGateway;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ListarUsuariosUsecaseImpl implements ListarUsuariosUsecase {

	private final UsuarioGateway usuarioGateway;

	@Override
	public List<Usuario> listar() {
		return usuarioGateway.listar();
	}
}

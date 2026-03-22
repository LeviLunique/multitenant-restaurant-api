package com.restauranthub.multitenant_restaurant_api.core.usecase;

import java.util.List;

import com.restauranthub.multitenant_restaurant_api.core.domain.Usuario;

public interface ListarUsuariosUsecase {

	List<Usuario> listar();
}

package com.restauranthub.multitenant_restaurant_api.core.usecase;

import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuario;

public interface BuscarTipoUsuarioPorIdUsecase {

	TipoUsuario obterPorId(Long id);
}

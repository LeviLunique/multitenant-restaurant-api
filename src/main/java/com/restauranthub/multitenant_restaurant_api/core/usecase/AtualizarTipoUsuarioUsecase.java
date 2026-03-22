package com.restauranthub.multitenant_restaurant_api.core.usecase;

import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuario;

public interface AtualizarTipoUsuarioUsecase {

	TipoUsuario atualizar(TipoUsuario tipoUsuario);
}

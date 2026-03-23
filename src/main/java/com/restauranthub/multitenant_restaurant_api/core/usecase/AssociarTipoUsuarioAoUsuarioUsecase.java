package com.restauranthub.multitenant_restaurant_api.core.usecase;

import com.restauranthub.multitenant_restaurant_api.core.domain.Usuario;

public interface AssociarTipoUsuarioAoUsuarioUsecase {

	Usuario associar(Long usuarioId, Long tipoUsuarioId);
}

package com.restauranthub.multitenant_restaurant_api.infra.database.mapper;

import org.springframework.stereotype.Component;

import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuario;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.entity.TipoUsuarioEntity;

@Component
public class TipoUsuarioEntityMapper {

	public TipoUsuario map(TipoUsuarioEntity entity) {
		return new TipoUsuario(entity.getId(), entity.getNome(), entity.getTipo());
	}

	public TipoUsuarioEntity map(TipoUsuario tipoUsuario) {
		return new TipoUsuarioEntity(tipoUsuario.getId(), tipoUsuario.getNome(), tipoUsuario.getTipo());
	}
}

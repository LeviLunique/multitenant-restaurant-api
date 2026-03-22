package com.restauranthub.multitenant_restaurant_api.infra.database.mapper;

import org.springframework.stereotype.Component;

import com.restauranthub.multitenant_restaurant_api.core.domain.Usuario;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.entity.UsuarioEntity;

@Component
public class UsuarioEntityMapper {

	public Usuario map(UsuarioEntity entity) {
		return new Usuario(entity.getId(), entity.getNome(), entity.getEmail());
	}

	public UsuarioEntity map(Usuario usuario) {
		return new UsuarioEntity(usuario.getId(), usuario.getNome(), usuario.getEmail());
	}
}

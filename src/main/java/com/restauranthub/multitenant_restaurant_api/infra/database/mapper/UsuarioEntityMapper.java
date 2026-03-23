package com.restauranthub.multitenant_restaurant_api.infra.database.mapper;

import java.util.LinkedHashSet;

import org.springframework.stereotype.Component;

import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuario;
import com.restauranthub.multitenant_restaurant_api.core.domain.Usuario;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.entity.TipoUsuarioEntity;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.entity.UsuarioEntity;

@Component
public class UsuarioEntityMapper {

	public Usuario map(UsuarioEntity entity) {
		return new Usuario(
				entity.getId(),
				entity.getNome(),
				entity.getEmail(),
				entity.getTiposUsuario().stream()
						.map(this::mapTipoUsuario)
						.collect(java.util.stream.Collectors.toCollection(LinkedHashSet::new)));
	}

	public UsuarioEntity map(Usuario usuario) {
		return new UsuarioEntity(
				usuario.getId(),
				usuario.getNome(),
				usuario.getEmail(),
				usuario.getTiposUsuario().stream()
						.map(this::mapTipoUsuario)
						.collect(java.util.stream.Collectors.toCollection(LinkedHashSet::new)));
	}

	private TipoUsuario mapTipoUsuario(TipoUsuarioEntity entity) {
		return new TipoUsuario(entity.getId(), entity.getNome(), entity.getTipo());
	}

	private TipoUsuarioEntity mapTipoUsuario(TipoUsuario tipoUsuario) {
		return new TipoUsuarioEntity(tipoUsuario.getId(), tipoUsuario.getNome(), tipoUsuario.getTipo());
	}
}

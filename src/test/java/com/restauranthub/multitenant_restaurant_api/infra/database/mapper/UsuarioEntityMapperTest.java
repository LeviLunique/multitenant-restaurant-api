package com.restauranthub.multitenant_restaurant_api.infra.database.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.restauranthub.multitenant_restaurant_api.core.domain.Usuario;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.entity.UsuarioEntity;

class UsuarioEntityMapperTest {

	private final UsuarioEntityMapper mapper = new UsuarioEntityMapper();

	@Test
	void shouldMapEntityToDomain() {
		var entity = new UsuarioEntity(1L, "Levi Lunique", "levi@example.com");

		var usuario = mapper.map(entity);

		assertEquals(1L, usuario.getId());
		assertEquals("Levi Lunique", usuario.getNome());
		assertEquals("levi@example.com", usuario.getEmail());
	}

	@Test
	void shouldMapDomainToEntity() {
		var usuario = new Usuario(1L, "Levi Lunique", "levi@example.com");

		var entity = mapper.map(usuario);

		assertEquals(1L, entity.getId());
		assertEquals("Levi Lunique", entity.getNome());
		assertEquals("levi@example.com", entity.getEmail());
	}
}

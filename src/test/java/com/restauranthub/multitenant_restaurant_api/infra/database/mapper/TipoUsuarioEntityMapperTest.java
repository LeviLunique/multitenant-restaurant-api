package com.restauranthub.multitenant_restaurant_api.infra.database.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuario;
import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuarioEnum;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.entity.TipoUsuarioEntity;

class TipoUsuarioEntityMapperTest {

	private final TipoUsuarioEntityMapper mapper = new TipoUsuarioEntityMapper();

	@Test
	void shouldMapEntityToDomain() {
		var entity = new TipoUsuarioEntity(1L, "Cliente", TipoUsuarioEnum.CLIENTE);

		var tipoUsuario = mapper.map(entity);

		assertEquals(1L, tipoUsuario.getId());
		assertEquals("Cliente", tipoUsuario.getNome());
		assertEquals(TipoUsuarioEnum.CLIENTE, tipoUsuario.getTipo());
	}

	@Test
	void shouldMapDomainToEntity() {
		var tipoUsuario = new TipoUsuario(1L, "Cliente", TipoUsuarioEnum.CLIENTE);

		var entity = mapper.map(tipoUsuario);

		assertEquals(1L, entity.getId());
		assertEquals("Cliente", entity.getNome());
		assertEquals(TipoUsuarioEnum.CLIENTE, entity.getTipo());
	}
}

package com.restauranthub.multitenant_restaurant_api.infra.database.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.restauranthub.multitenant_restaurant_api.core.domain.Restaurante;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.entity.RestauranteEntity;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.entity.UsuarioEntity;

class RestauranteEntityMapperTest {

	private final RestauranteEntityMapper mapper = new RestauranteEntityMapper();

	@Test
	void shouldMapEntityToDomain() {
		var entity = new RestauranteEntity(1L, "Bistrô Central", "Rua A, 100", "Francesa", "09:00-22:00",
				new UsuarioEntity(10L, "Dono", "dono@example.com"));

		var restaurante = mapper.map(entity);

		assertEquals(1L, restaurante.getId());
		assertEquals("Bistrô Central", restaurante.getNome());
		assertEquals(10L, restaurante.getDonoUsuarioId());
	}

	@Test
	void shouldMapDomainToEntity() {
		var restaurante = new Restaurante(1L, "Bistrô Central", "Rua A, 100", "Francesa", "09:00-22:00", 10L);

		var entity = mapper.map(restaurante);

		assertEquals(1L, entity.getId());
		assertEquals("Bistrô Central", entity.getNome());
		assertEquals(10L, entity.getDono().getId());
	}
}

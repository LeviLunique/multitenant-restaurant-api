package com.restauranthub.multitenant_restaurant_api.infra.database.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.restauranthub.multitenant_restaurant_api.core.domain.ItemCardapio;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.entity.ItemCardapioEntity;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.entity.RestauranteEntity;

class ItemCardapioEntityMapperTest {

	private final ItemCardapioEntityMapper mapper = new ItemCardapioEntityMapper();

	@Test
	void shouldMapEntityToDomain() {
		var entity = new ItemCardapioEntity(
				1L,
				"Risoto",
				"Descricao",
				new BigDecimal("49.90"),
				true,
				"/foto.jpg",
				new RestauranteEntity(10L, "Bistro", "Rua A", "Francesa", "09:00-22:00", null));

		var itemCardapio = mapper.map(entity);

		assertEquals(1L, itemCardapio.getId());
		assertEquals(10L, itemCardapio.getRestauranteId());
	}

	@Test
	void shouldMapDomainToEntity() {
		var itemCardapio = new ItemCardapio(1L, 10L, "Risoto", "Descricao", new BigDecimal("49.90"), true, "/foto.jpg");

		var entity = mapper.map(itemCardapio);

		assertEquals(1L, entity.getId());
		assertEquals(10L, entity.getRestaurante().getId());
		assertEquals("Risoto", entity.getNome());
	}
}

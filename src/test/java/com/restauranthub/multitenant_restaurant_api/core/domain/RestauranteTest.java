package com.restauranthub.multitenant_restaurant_api.core.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.restauranthub.multitenant_restaurant_api.core.exception.BusinessException;

class RestauranteTest {

	@Test
	void shouldCreateRestaurantWhenDataIsValid() {
		var restaurante = new Restaurante(1L, "Bistrô Central", "Rua A, 100", "Francesa", "09:00-22:00", 10L);

		assertEquals(1L, restaurante.getId());
		assertEquals("Bistrô Central", restaurante.getNome());
		assertEquals("Rua A, 100", restaurante.getEndereco());
		assertEquals("Francesa", restaurante.getTipoCozinha());
		assertEquals("09:00-22:00", restaurante.getHorarioFuncionamento());
		assertEquals(10L, restaurante.getDonoUsuarioId());
	}

	@Test
	void shouldRejectBlankName() {
		var exception = assertThrows(BusinessException.class,
				() -> new Restaurante(1L, " ", "Rua A, 100", "Francesa", "09:00-22:00", 10L));

		assertEquals("INVALID_RESTAURANT_NAME", exception.getCode());
	}

	@Test
	void shouldRejectBlankAddress() {
		var exception = assertThrows(BusinessException.class,
				() -> new Restaurante(1L, "Bistrô Central", " ", "Francesa", "09:00-22:00", 10L));

		assertEquals("INVALID_RESTAURANT_ADDRESS", exception.getCode());
	}

	@Test
	void shouldRejectBlankCuisineType() {
		var exception = assertThrows(BusinessException.class,
				() -> new Restaurante(1L, "Bistrô Central", "Rua A, 100", " ", "09:00-22:00", 10L));

		assertEquals("INVALID_RESTAURANT_CUISINE_TYPE", exception.getCode());
	}

	@Test
	void shouldRejectBlankOpeningHours() {
		var exception = assertThrows(BusinessException.class,
				() -> new Restaurante(1L, "Bistrô Central", "Rua A, 100", "Francesa", " ", 10L));

		assertEquals("INVALID_RESTAURANT_OPENING_HOURS", exception.getCode());
	}

	@Test
	void shouldRejectMissingOwner() {
		var exception = assertThrows(BusinessException.class,
				() -> new Restaurante(1L, "Bistrô Central", "Rua A, 100", "Francesa", "09:00-22:00", null));

		assertEquals("INVALID_RESTAURANT_OWNER", exception.getCode());
	}
}

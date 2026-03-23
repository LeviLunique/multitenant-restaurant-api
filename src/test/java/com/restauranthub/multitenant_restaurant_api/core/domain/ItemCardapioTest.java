package com.restauranthub.multitenant_restaurant_api.core.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.restauranthub.multitenant_restaurant_api.core.exception.BusinessException;

class ItemCardapioTest {

	@Test
	void shouldCreateMenuItemWithValidData() {
		var itemCardapio = new ItemCardapio(1L, 10L, "Risoto", "Risoto de cogumelos", new BigDecimal("49.90"), true, "/fotos/risoto.jpg");

		assertEquals(1L, itemCardapio.getId());
		assertEquals(10L, itemCardapio.getRestauranteId());
		assertEquals("Risoto", itemCardapio.getNome());
		assertEquals("Risoto de cogumelos", itemCardapio.getDescricao());
		assertEquals(new BigDecimal("49.90"), itemCardapio.getPreco());
		assertEquals(true, itemCardapio.getApenasConsumoNoLocal());
		assertEquals("/fotos/risoto.jpg", itemCardapio.getCaminhoFoto());
	}

	@Test
	void shouldRejectBlankName() {
		assertThrows(BusinessException.class, () -> new ItemCardapio(1L, 10L, " ", "Descricao", new BigDecimal("49.90"), true, "/foto.jpg"));
	}

	@Test
	void shouldRejectBlankDescription() {
		assertThrows(BusinessException.class, () -> new ItemCardapio(1L, 10L, "Risoto", " ", new BigDecimal("49.90"), true, "/foto.jpg"));
	}

	@Test
	void shouldRejectInvalidPrice() {
		assertThrows(BusinessException.class, () -> new ItemCardapio(1L, 10L, "Risoto", "Descricao", BigDecimal.ZERO, true, "/foto.jpg"));
	}

	@Test
	void shouldRejectNullLocalConsumptionAvailability() {
		assertThrows(BusinessException.class, () -> new ItemCardapio(1L, 10L, "Risoto", "Descricao", new BigDecimal("49.90"), null, "/foto.jpg"));
	}

	@Test
	void shouldRejectBlankPhotoPath() {
		assertThrows(BusinessException.class, () -> new ItemCardapio(1L, 10L, "Risoto", "Descricao", new BigDecimal("49.90"), true, " "));
	}

	@Test
	void shouldRejectInvalidRestaurantId() {
		assertThrows(BusinessException.class, () -> new ItemCardapio(1L, 0L, "Risoto", "Descricao", new BigDecimal("49.90"), true, "/foto.jpg"));
	}
}

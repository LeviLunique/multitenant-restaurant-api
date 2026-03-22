package com.restauranthub.multitenant_restaurant_api.core.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.restauranthub.multitenant_restaurant_api.core.exception.BusinessException;

class UsuarioTest {

	@Test
	void shouldCreateUserWhenNameAndEmailAreValid() {
		var usuario = new Usuario(1L, "Levi Lunique", "levi@example.com");

		assertEquals(1L, usuario.getId());
		assertEquals("Levi Lunique", usuario.getNome());
		assertEquals("levi@example.com", usuario.getEmail());
	}

	@Test
	void shouldRejectBlankName() {
		var exception = assertThrows(BusinessException.class, () -> new Usuario(1L, " ", "levi@example.com"));

		assertEquals("INVALID_USER_NAME", exception.getCode());
	}

	@Test
	void shouldRejectInvalidEmail() {
		var exception = assertThrows(BusinessException.class, () -> new Usuario(1L, "Levi Lunique", "invalid-email"));

		assertEquals("INVALID_USER_EMAIL", exception.getCode());
	}
}

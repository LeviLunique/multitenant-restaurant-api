package com.restauranthub.multitenant_restaurant_api.core.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.restauranthub.multitenant_restaurant_api.core.exception.BusinessException;

class TipoUsuarioTest {

	@Test
	void shouldCreateUserTypeWhenNameAndTypeAreValid() {
		var tipoUsuario = new TipoUsuario(1L, " Cliente ", TipoUsuarioEnum.CLIENTE);

		assertEquals(1L, tipoUsuario.getId());
		assertEquals("Cliente", tipoUsuario.getNome());
		assertEquals(TipoUsuarioEnum.CLIENTE, tipoUsuario.getTipo());
	}

	@Test
	void shouldRejectBlankUserTypeName() {
		var exception = assertThrows(BusinessException.class, () -> new TipoUsuario(1L, " ", TipoUsuarioEnum.CLIENTE));

		assertEquals("INVALID_USER_TYPE_NAME", exception.getCode());
	}

	@Test
	void shouldRejectMissingUserTypeEnum() {
		var exception = assertThrows(BusinessException.class, () -> new TipoUsuario(1L, "Cliente", null));

		assertEquals("INVALID_USER_TYPE_ENUM", exception.getCode());
	}
}

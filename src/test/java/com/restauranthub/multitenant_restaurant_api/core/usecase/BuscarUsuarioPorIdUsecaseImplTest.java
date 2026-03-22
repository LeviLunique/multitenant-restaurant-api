package com.restauranthub.multitenant_restaurant_api.core.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.restauranthub.multitenant_restaurant_api.core.domain.Usuario;
import com.restauranthub.multitenant_restaurant_api.core.exception.ResourceNotFoundException;
import com.restauranthub.multitenant_restaurant_api.core.gateway.UsuarioGateway;

class BuscarUsuarioPorIdUsecaseImplTest {

	private final UsuarioGateway usuarioGateway = Mockito.mock(UsuarioGateway.class);
	private final BuscarUsuarioPorIdUsecaseImpl usecase = new BuscarUsuarioPorIdUsecaseImpl(usuarioGateway);

	@Test
	void shouldReturnUserWhenItExists() {
		when(usuarioGateway.obterPorId(1L)).thenReturn(Optional.of(new Usuario(1L, "Levi Lunique", "levi@example.com")));

		var usuario = usecase.obterPorId(1L);

		assertEquals(1L, usuario.getId());
	}

	@Test
	void shouldThrowWhenUserDoesNotExist() {
		when(usuarioGateway.obterPorId(1L)).thenReturn(Optional.empty());

		var exception = assertThrows(ResourceNotFoundException.class, () -> usecase.obterPorId(1L));

		assertEquals("USER_NOT_FOUND", exception.getCode());
	}
}

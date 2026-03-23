package com.restauranthub.multitenant_restaurant_api.core.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.restauranthub.multitenant_restaurant_api.core.domain.Usuario;
import com.restauranthub.multitenant_restaurant_api.core.exception.ResourceNotFoundException;
import com.restauranthub.multitenant_restaurant_api.core.gateway.UsuarioGateway;

class RemoverUsuarioUsecaseImplTest {

	private final UsuarioGateway usuarioGateway = Mockito.mock(UsuarioGateway.class);
	private final RemoverUsuarioUsecaseImpl usecase = new RemoverUsuarioUsecaseImpl(usuarioGateway);

	@Test
	void shouldRemoveExistingUser() {
		when(usuarioGateway.obterPorId(1L)).thenReturn(Optional.of(new Usuario(1L, "Levi", "levi@example.com")));

		usecase.remover(1L);

		verify(usuarioGateway).remover(1L);
	}

	@Test
	void shouldRejectMissingUserDuringRemoval() {
		when(usuarioGateway.obterPorId(99L)).thenReturn(Optional.empty());

		var exception = assertThrows(ResourceNotFoundException.class, () -> usecase.remover(99L));

		assertEquals("USER_NOT_FOUND", exception.getCode());
		verify(usuarioGateway, Mockito.never()).remover(99L);
	}
}

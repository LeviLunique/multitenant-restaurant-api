package com.restauranthub.multitenant_restaurant_api.core.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.restauranthub.multitenant_restaurant_api.core.domain.Usuario;
import com.restauranthub.multitenant_restaurant_api.core.exception.BusinessException;
import com.restauranthub.multitenant_restaurant_api.core.exception.ResourceNotFoundException;
import com.restauranthub.multitenant_restaurant_api.core.gateway.UsuarioGateway;

class AtualizarUsuarioUsecaseImplTest {

	private final UsuarioGateway usuarioGateway = Mockito.mock(UsuarioGateway.class);
	private final AtualizarUsuarioUsecaseImpl usecase = new AtualizarUsuarioUsecaseImpl(usuarioGateway);

	@Test
	void shouldUpdateUserWhenEmailIsAvailable() {
		var usuarioAtualizado = new Usuario(1L, "Levi Atualizado", "levi.atualizado@example.com");
		when(usuarioGateway.obterPorId(1L)).thenReturn(Optional.of(new Usuario(1L, "Levi Lunique", "levi@example.com")));
		when(usuarioGateway.obterPorEmail("levi.atualizado@example.com")).thenReturn(Optional.empty());
		when(usuarioGateway.atualizar(usuarioAtualizado)).thenReturn(usuarioAtualizado);

		var usuario = usecase.atualizar(usuarioAtualizado);

		assertEquals(1L, usuario.getId());
		assertEquals("Levi Atualizado", usuario.getNome());
		assertEquals("levi.atualizado@example.com", usuario.getEmail());
		verify(usuarioGateway).atualizar(usuarioAtualizado);
	}

	@Test
	void shouldAllowUpdatingUserWithSameEmail() {
		var usuarioAtualizado = new Usuario(1L, "Levi Atualizado", "levi@example.com");
		when(usuarioGateway.obterPorId(1L)).thenReturn(Optional.of(new Usuario(1L, "Levi Lunique", "levi@example.com")));
		when(usuarioGateway.obterPorEmail("levi@example.com"))
				.thenReturn(Optional.of(new Usuario(1L, "Levi Lunique", "levi@example.com")));
		when(usuarioGateway.atualizar(usuarioAtualizado)).thenReturn(usuarioAtualizado);

		var usuario = usecase.atualizar(usuarioAtualizado);

		assertEquals("Levi Atualizado", usuario.getNome());
		verify(usuarioGateway).atualizar(usuarioAtualizado);
	}

	@Test
	void shouldRejectMissingUserDuringUpdate() {
		when(usuarioGateway.obterPorId(99L)).thenReturn(Optional.empty());

		var exception = assertThrows(ResourceNotFoundException.class,
				() -> usecase.atualizar(new Usuario(99L, "Levi", "levi@example.com")));

		assertEquals("USER_NOT_FOUND", exception.getCode());
	}

	@Test
	void shouldRejectDuplicatedEmailDuringUpdate() {
		when(usuarioGateway.obterPorId(1L)).thenReturn(Optional.of(new Usuario(1L, "Levi", "levi@example.com")));
		when(usuarioGateway.obterPorEmail("maria@example.com"))
				.thenReturn(Optional.of(new Usuario(2L, "Maria", "maria@example.com")));

		var exception = assertThrows(BusinessException.class,
				() -> usecase.atualizar(new Usuario(1L, "Levi", "maria@example.com")));

		assertEquals("USER_EMAIL_ALREADY_EXISTS", exception.getCode());
		verify(usuarioGateway, Mockito.never()).atualizar(any(Usuario.class));
	}
}

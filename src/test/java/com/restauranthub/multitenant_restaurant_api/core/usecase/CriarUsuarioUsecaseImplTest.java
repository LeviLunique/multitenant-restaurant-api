package com.restauranthub.multitenant_restaurant_api.core.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.restauranthub.multitenant_restaurant_api.core.domain.Usuario;
import com.restauranthub.multitenant_restaurant_api.core.exception.BusinessException;
import com.restauranthub.multitenant_restaurant_api.core.gateway.UsuarioGateway;

class CriarUsuarioUsecaseImplTest {

	private final UsuarioGateway usuarioGateway = Mockito.mock(UsuarioGateway.class);
	private final CriarUsuarioUsecaseImpl usecase = new CriarUsuarioUsecaseImpl(usuarioGateway);

	@Test
	void shouldCreateUserWhenEmailDoesNotExist() {
		var usuario = new Usuario(null, "Levi Lunique", "levi@example.com");
		when(usuarioGateway.obterPorEmail("levi@example.com")).thenReturn(Optional.empty());
		when(usuarioGateway.criar(usuario)).thenReturn(1L);

		var id = usecase.criar(usuario);

		assertEquals(1L, id);
		verify(usuarioGateway).criar(usuario);
	}

	@Test
	void shouldRejectDuplicatedEmail() {
		var usuario = new Usuario(null, "Levi Lunique", "levi@example.com");
		when(usuarioGateway.obterPorEmail("levi@example.com")).thenReturn(Optional.of(new Usuario(1L, "Existing", "levi@example.com")));

		var exception = assertThrows(BusinessException.class, () -> usecase.criar(usuario));

		assertEquals("USER_EMAIL_ALREADY_EXISTS", exception.getCode());
	}
}

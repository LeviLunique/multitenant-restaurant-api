package com.restauranthub.multitenant_restaurant_api.core.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.restauranthub.multitenant_restaurant_api.core.domain.Usuario;
import com.restauranthub.multitenant_restaurant_api.core.gateway.UsuarioGateway;

class ListarUsuariosUsecaseImplTest {

	private final UsuarioGateway usuarioGateway = Mockito.mock(UsuarioGateway.class);
	private final ListarUsuariosUsecaseImpl usecase = new ListarUsuariosUsecaseImpl(usuarioGateway);

	@Test
	void shouldListUsersFromGateway() {
		when(usuarioGateway.listar()).thenReturn(List.of(
				new Usuario(1L, "Levi Lunique", "levi@example.com"),
				new Usuario(2L, "Maria Silva", "maria@example.com")));

		var usuarios = usecase.listar();

		assertEquals(2, usuarios.size());
		assertEquals("Levi Lunique", usuarios.get(0).getNome());
	}
}

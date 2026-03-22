package com.restauranthub.multitenant_restaurant_api.core.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuario;
import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuarioEnum;
import com.restauranthub.multitenant_restaurant_api.core.domain.Usuario;
import com.restauranthub.multitenant_restaurant_api.core.exception.BusinessException;
import com.restauranthub.multitenant_restaurant_api.core.exception.ResourceNotFoundException;
import com.restauranthub.multitenant_restaurant_api.core.gateway.TipoUsuarioGateway;
import com.restauranthub.multitenant_restaurant_api.core.gateway.UsuarioGateway;

class AssociarTipoUsuarioAoUsuarioUsecaseImplTest {

	private final UsuarioGateway usuarioGateway = Mockito.mock(UsuarioGateway.class);
	private final TipoUsuarioGateway tipoUsuarioGateway = Mockito.mock(TipoUsuarioGateway.class);
	private final AssociarTipoUsuarioAoUsuarioUsecaseImpl usecase = new AssociarTipoUsuarioAoUsuarioUsecaseImpl(
			usuarioGateway,
			tipoUsuarioGateway);

	@Test
	void shouldAssociateUserTypeToUser() {
		var usuarioExistente = new Usuario(1L, "Levi Lunique", "levi@example.com");
		var tipoUsuario = new TipoUsuario(2L, "Cliente", TipoUsuarioEnum.CLIENTE);
		var usuarioAtualizado = new Usuario(1L, "Levi Lunique", "levi@example.com");
		usuarioAtualizado.associarTipoUsuario(tipoUsuario);
		when(usuarioGateway.obterPorId(1L)).thenReturn(Optional.of(usuarioExistente));
		when(tipoUsuarioGateway.obterPorId(2L)).thenReturn(Optional.of(tipoUsuario));
		when(usuarioGateway.atualizar(usuarioAtualizado)).thenReturn(usuarioAtualizado);

		var usuario = usecase.associar(1L, 2L);

		assertEquals(1L, usuario.getId());
		assertEquals(1, usuario.getTiposUsuario().size());
		assertEquals("Cliente", usuario.getTiposUsuario().iterator().next().getNome());
		assertEquals(TipoUsuarioEnum.CLIENTE, usuario.getTiposUsuario().iterator().next().getTipo());
		verify(usuarioGateway).atualizar(usuarioAtualizado);
	}

	@Test
	void shouldRejectMissingUserDuringAssociation() {
		when(usuarioGateway.obterPorId(99L)).thenReturn(Optional.empty());

		var exception = assertThrows(ResourceNotFoundException.class, () -> usecase.associar(99L, 1L));

		assertEquals("USER_NOT_FOUND", exception.getCode());
	}

	@Test
	void shouldRejectMissingUserTypeDuringAssociation() {
		when(usuarioGateway.obterPorId(1L)).thenReturn(Optional.of(new Usuario(1L, "Levi Lunique", "levi@example.com")));
		when(tipoUsuarioGateway.obterPorId(99L)).thenReturn(Optional.empty());

		var exception = assertThrows(ResourceNotFoundException.class, () -> usecase.associar(1L, 99L));

		assertEquals("USER_TYPE_NOT_FOUND", exception.getCode());
	}

	@Test
	void shouldRejectDuplicatedAssociation() {
		var usuario = new Usuario(1L, "Levi Lunique", "levi@example.com");
		usuario.associarTipoUsuario(new TipoUsuario(2L, "Cliente", TipoUsuarioEnum.CLIENTE));
		when(usuarioGateway.obterPorId(1L)).thenReturn(Optional.of(usuario));
		when(tipoUsuarioGateway.obterPorId(2L))
				.thenReturn(Optional.of(new TipoUsuario(2L, "Cliente", TipoUsuarioEnum.CLIENTE)));

		var exception = assertThrows(BusinessException.class, () -> usecase.associar(1L, 2L));

		assertEquals("USER_ALREADY_ASSOCIATED_WITH_TYPE", exception.getCode());
	}
}

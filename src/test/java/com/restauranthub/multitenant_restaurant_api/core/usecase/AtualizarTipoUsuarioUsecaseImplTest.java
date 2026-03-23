package com.restauranthub.multitenant_restaurant_api.core.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuario;
import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuarioEnum;
import com.restauranthub.multitenant_restaurant_api.core.exception.BusinessException;
import com.restauranthub.multitenant_restaurant_api.core.exception.ResourceNotFoundException;
import com.restauranthub.multitenant_restaurant_api.core.gateway.TipoUsuarioGateway;

class AtualizarTipoUsuarioUsecaseImplTest {

	private final TipoUsuarioGateway tipoUsuarioGateway = Mockito.mock(TipoUsuarioGateway.class);
	private final AtualizarTipoUsuarioUsecaseImpl usecase = new AtualizarTipoUsuarioUsecaseImpl(tipoUsuarioGateway);

	@Test
	void shouldUpdateUserTypeWhenNameAndEnumAreAvailable() {
		var tipoUsuarioAtualizado = new TipoUsuario(1L, "Dono de Restaurante", TipoUsuarioEnum.DONO);
		when(tipoUsuarioGateway.obterPorId(1L))
				.thenReturn(Optional.of(new TipoUsuario(1L, "Cliente", TipoUsuarioEnum.CLIENTE)));
		when(tipoUsuarioGateway.obterPorNome("Dono de Restaurante")).thenReturn(Optional.empty());
		when(tipoUsuarioGateway.obterPorTipo(TipoUsuarioEnum.DONO)).thenReturn(Optional.empty());
		when(tipoUsuarioGateway.atualizar(tipoUsuarioAtualizado)).thenReturn(tipoUsuarioAtualizado);

		var tipoUsuario = usecase.atualizar(tipoUsuarioAtualizado);

		assertEquals(1L, tipoUsuario.getId());
		assertEquals("Dono de Restaurante", tipoUsuario.getNome());
		verify(tipoUsuarioGateway).atualizar(tipoUsuarioAtualizado);
	}

	@Test
	void shouldAllowUpdatingUserTypeWithSameName() {
		var tipoUsuarioAtualizado = new TipoUsuario(1L, "Cliente", TipoUsuarioEnum.CLIENTE);
		when(tipoUsuarioGateway.obterPorId(1L))
				.thenReturn(Optional.of(new TipoUsuario(1L, "Cliente", TipoUsuarioEnum.CLIENTE)));
		when(tipoUsuarioGateway.obterPorNome("Cliente"))
				.thenReturn(Optional.of(new TipoUsuario(1L, "Cliente", TipoUsuarioEnum.CLIENTE)));
		when(tipoUsuarioGateway.obterPorTipo(TipoUsuarioEnum.CLIENTE))
				.thenReturn(Optional.of(new TipoUsuario(1L, "Cliente", TipoUsuarioEnum.CLIENTE)));
		when(tipoUsuarioGateway.atualizar(tipoUsuarioAtualizado)).thenReturn(tipoUsuarioAtualizado);

		var tipoUsuario = usecase.atualizar(tipoUsuarioAtualizado);

		assertEquals("Cliente", tipoUsuario.getNome());
		verify(tipoUsuarioGateway).atualizar(tipoUsuarioAtualizado);
	}

	@Test
	void shouldRejectMissingUserTypeDuringUpdate() {
		var tipoUsuarioAtualizado = new TipoUsuario(99L, "Cliente", TipoUsuarioEnum.CLIENTE);
		when(tipoUsuarioGateway.obterPorId(99L)).thenReturn(Optional.empty());

		var exception = assertThrows(ResourceNotFoundException.class, () -> usecase.atualizar(tipoUsuarioAtualizado));

		assertEquals("USER_TYPE_NOT_FOUND", exception.getCode());
	}

	@Test
	void shouldRejectDuplicatedUserTypeNameDuringUpdate() {
		var tipoUsuarioAtualizado = new TipoUsuario(1L, "Dono de Restaurante", TipoUsuarioEnum.DONO);
		when(tipoUsuarioGateway.obterPorId(1L))
				.thenReturn(Optional.of(new TipoUsuario(1L, "Cliente", TipoUsuarioEnum.CLIENTE)));
		when(tipoUsuarioGateway.obterPorNome("Dono de Restaurante"))
				.thenReturn(Optional.of(new TipoUsuario(2L, "Dono de Restaurante", TipoUsuarioEnum.DONO)));

		var exception = assertThrows(BusinessException.class, () -> usecase.atualizar(tipoUsuarioAtualizado));

		assertEquals("USER_TYPE_NAME_ALREADY_EXISTS", exception.getCode());
		verify(tipoUsuarioGateway, Mockito.never()).atualizar(any(TipoUsuario.class));
	}

	@Test
	void shouldRejectDuplicatedUserTypeEnumDuringUpdate() {
		var tipoUsuarioAtualizado = new TipoUsuario(1L, "Dono Restaurante", TipoUsuarioEnum.DONO);
		when(tipoUsuarioGateway.obterPorId(1L))
				.thenReturn(Optional.of(new TipoUsuario(1L, "Cliente", TipoUsuarioEnum.CLIENTE)));
		when(tipoUsuarioGateway.obterPorNome("Dono Restaurante")).thenReturn(Optional.empty());
		when(tipoUsuarioGateway.obterPorTipo(TipoUsuarioEnum.DONO))
				.thenReturn(Optional.of(new TipoUsuario(2L, "Dono de Restaurante", TipoUsuarioEnum.DONO)));

		var exception = assertThrows(BusinessException.class, () -> usecase.atualizar(tipoUsuarioAtualizado));

		assertEquals("USER_TYPE_ENUM_ALREADY_EXISTS", exception.getCode());
		verify(tipoUsuarioGateway, Mockito.never()).atualizar(any(TipoUsuario.class));
	}
}

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
import com.restauranthub.multitenant_restaurant_api.core.exception.ResourceNotFoundException;
import com.restauranthub.multitenant_restaurant_api.core.gateway.TipoUsuarioGateway;

class RemoverTipoUsuarioUsecaseImplTest {

	private final TipoUsuarioGateway tipoUsuarioGateway = Mockito.mock(TipoUsuarioGateway.class);
	private final RemoverTipoUsuarioUsecaseImpl usecase = new RemoverTipoUsuarioUsecaseImpl(tipoUsuarioGateway);

	@Test
	void shouldRemoveExistingUserType() {
		when(tipoUsuarioGateway.obterPorId(1L))
				.thenReturn(Optional.of(new TipoUsuario(1L, "Cliente", TipoUsuarioEnum.CLIENTE)));

		usecase.remover(1L);

		verify(tipoUsuarioGateway).remover(1L);
	}

	@Test
	void shouldRejectMissingUserTypeDuringRemoval() {
		when(tipoUsuarioGateway.obterPorId(99L)).thenReturn(Optional.empty());

		var exception = assertThrows(ResourceNotFoundException.class, () -> usecase.remover(99L));

		assertEquals("USER_TYPE_NOT_FOUND", exception.getCode());
		verify(tipoUsuarioGateway, Mockito.never()).remover(99L);
	}
}

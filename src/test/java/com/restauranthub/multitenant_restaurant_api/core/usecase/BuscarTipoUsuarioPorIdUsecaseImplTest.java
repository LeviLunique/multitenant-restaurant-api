package com.restauranthub.multitenant_restaurant_api.core.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuario;
import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuarioEnum;
import com.restauranthub.multitenant_restaurant_api.core.exception.ResourceNotFoundException;
import com.restauranthub.multitenant_restaurant_api.core.gateway.TipoUsuarioGateway;

class BuscarTipoUsuarioPorIdUsecaseImplTest {

	private final TipoUsuarioGateway tipoUsuarioGateway = Mockito.mock(TipoUsuarioGateway.class);
	private final BuscarTipoUsuarioPorIdUsecaseImpl usecase = new BuscarTipoUsuarioPorIdUsecaseImpl(tipoUsuarioGateway);

	@Test
	void shouldReturnUserTypeWhenItExists() {
		when(tipoUsuarioGateway.obterPorId(1L))
				.thenReturn(Optional.of(new TipoUsuario(1L, "Cliente", TipoUsuarioEnum.CLIENTE)));

		var tipoUsuario = usecase.obterPorId(1L);

		assertEquals(1L, tipoUsuario.getId());
		assertEquals("Cliente", tipoUsuario.getNome());
		assertEquals(TipoUsuarioEnum.CLIENTE, tipoUsuario.getTipo());
	}

	@Test
	void shouldRejectMissingUserType() {
		when(tipoUsuarioGateway.obterPorId(99L)).thenReturn(Optional.empty());

		var exception = assertThrows(ResourceNotFoundException.class, () -> usecase.obterPorId(99L));

		assertEquals("USER_TYPE_NOT_FOUND", exception.getCode());
	}
}

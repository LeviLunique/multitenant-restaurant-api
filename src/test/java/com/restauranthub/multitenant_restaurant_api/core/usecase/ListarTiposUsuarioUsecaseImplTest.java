package com.restauranthub.multitenant_restaurant_api.core.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuario;
import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuarioEnum;
import com.restauranthub.multitenant_restaurant_api.core.gateway.TipoUsuarioGateway;

class ListarTiposUsuarioUsecaseImplTest {

	private final TipoUsuarioGateway tipoUsuarioGateway = Mockito.mock(TipoUsuarioGateway.class);
	private final ListarTiposUsuarioUsecaseImpl usecase = new ListarTiposUsuarioUsecaseImpl(tipoUsuarioGateway);

	@Test
	void shouldListUserTypes() {
		when(tipoUsuarioGateway.listar()).thenReturn(List.of(
				new TipoUsuario(1L, "Cliente", TipoUsuarioEnum.CLIENTE),
				new TipoUsuario(2L, "Dono de Restaurante", TipoUsuarioEnum.DONO)));

		var tiposUsuario = usecase.listar();

		assertEquals(2, tiposUsuario.size());
		assertEquals("Cliente", tiposUsuario.get(0).getNome());
		assertEquals("Dono de Restaurante", tiposUsuario.get(1).getNome());
		verify(tipoUsuarioGateway).listar();
	}
}

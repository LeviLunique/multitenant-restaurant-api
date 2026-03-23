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
import com.restauranthub.multitenant_restaurant_api.core.exception.BusinessException;
import com.restauranthub.multitenant_restaurant_api.core.gateway.TipoUsuarioGateway;

class CriarTipoUsuarioUsecaseImplTest {

	private final TipoUsuarioGateway tipoUsuarioGateway = Mockito.mock(TipoUsuarioGateway.class);
	private final CriarTipoUsuarioUsecaseImpl usecase = new CriarTipoUsuarioUsecaseImpl(tipoUsuarioGateway);

	@Test
	void shouldCreateUserTypeWhenNameAndEnumDoNotExist() {
		var tipoUsuario = new TipoUsuario(null, "Cliente", TipoUsuarioEnum.CLIENTE);
		when(tipoUsuarioGateway.obterPorNome("Cliente")).thenReturn(Optional.empty());
		when(tipoUsuarioGateway.obterPorTipo(TipoUsuarioEnum.CLIENTE)).thenReturn(Optional.empty());
		when(tipoUsuarioGateway.criar(tipoUsuario)).thenReturn(1L);

		var id = usecase.criar(tipoUsuario);

		assertEquals(1L, id);
		verify(tipoUsuarioGateway).criar(tipoUsuario);
	}

	@Test
	void shouldRejectDuplicatedUserTypeName() {
		var tipoUsuario = new TipoUsuario(null, "Cliente", TipoUsuarioEnum.CLIENTE);
		when(tipoUsuarioGateway.obterPorNome("Cliente"))
				.thenReturn(Optional.of(new TipoUsuario(1L, "Cliente", TipoUsuarioEnum.CLIENTE)));

		var exception = assertThrows(BusinessException.class, () -> usecase.criar(tipoUsuario));

		assertEquals("USER_TYPE_NAME_ALREADY_EXISTS", exception.getCode());
	}

	@Test
	void shouldRejectDuplicatedUserTypeEnum() {
		var tipoUsuario = new TipoUsuario(null, "Cliente Premium", TipoUsuarioEnum.CLIENTE);
		when(tipoUsuarioGateway.obterPorNome("Cliente Premium")).thenReturn(Optional.empty());
		when(tipoUsuarioGateway.obterPorTipo(TipoUsuarioEnum.CLIENTE))
				.thenReturn(Optional.of(new TipoUsuario(1L, "Cliente", TipoUsuarioEnum.CLIENTE)));

		var exception = assertThrows(BusinessException.class, () -> usecase.criar(tipoUsuario));

		assertEquals("USER_TYPE_ENUM_ALREADY_EXISTS", exception.getCode());
	}
}

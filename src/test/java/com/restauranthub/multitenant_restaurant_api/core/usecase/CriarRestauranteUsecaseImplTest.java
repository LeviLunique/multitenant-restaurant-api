package com.restauranthub.multitenant_restaurant_api.core.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.restauranthub.multitenant_restaurant_api.core.domain.Restaurante;
import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuario;
import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuarioEnum;
import com.restauranthub.multitenant_restaurant_api.core.domain.Usuario;
import com.restauranthub.multitenant_restaurant_api.core.exception.BusinessException;
import com.restauranthub.multitenant_restaurant_api.core.exception.ResourceNotFoundException;
import com.restauranthub.multitenant_restaurant_api.core.gateway.RestauranteGateway;
import com.restauranthub.multitenant_restaurant_api.core.gateway.UsuarioGateway;

class CriarRestauranteUsecaseImplTest {

	private final RestauranteGateway restauranteGateway = Mockito.mock(RestauranteGateway.class);
	private final UsuarioGateway usuarioGateway = Mockito.mock(UsuarioGateway.class);
	private final CriarRestauranteUsecaseImpl usecase = new CriarRestauranteUsecaseImpl(restauranteGateway, usuarioGateway);

	@Test
	void shouldCreateRestaurantWhenOwnerExistsAndHasOwnerType() {
		var restaurante = new Restaurante(null, "Bistrô Central", "Rua A, 100", "Francesa", "09:00-22:00", 10L);
		var dono = criarUsuarioDono(10L);
		when(usuarioGateway.obterPorId(10L)).thenReturn(Optional.of(dono));
		when(restauranteGateway.criar(restaurante)).thenReturn(1L);

		var id = usecase.criar(restaurante);

		assertEquals(1L, id);
		verify(restauranteGateway).criar(restaurante);
	}

	@Test
	void shouldRejectMissingOwnerDuringCreation() {
		var restaurante = new Restaurante(null, "Bistrô Central", "Rua A, 100", "Francesa", "09:00-22:00", 10L);
		when(usuarioGateway.obterPorId(10L)).thenReturn(Optional.empty());

		var exception = assertThrows(ResourceNotFoundException.class, () -> usecase.criar(restaurante));

		assertEquals("RESTAURANT_OWNER_NOT_FOUND", exception.getCode());
	}

	@Test
	void shouldRejectOwnerWithoutOwnerTypeDuringCreation() {
		var restaurante = new Restaurante(null, "Bistrô Central", "Rua A, 100", "Francesa", "09:00-22:00", 10L);
		var cliente = new Usuario(10L, "Cliente", "cliente@example.com");
		cliente.associarTipoUsuario(new TipoUsuario(1L, "Cliente", TipoUsuarioEnum.CLIENTE));
		when(usuarioGateway.obterPorId(10L)).thenReturn(Optional.of(cliente));

		var exception = assertThrows(BusinessException.class, () -> usecase.criar(restaurante));

		assertEquals("INVALID_RESTAURANT_OWNER_TYPE", exception.getCode());
	}

	private Usuario criarUsuarioDono(Long id) {
		var dono = new Usuario(id, "Dono", "dono@example.com");
		dono.associarTipoUsuario(new TipoUsuario(2L, "Dono de Restaurante", TipoUsuarioEnum.DONO));
		return dono;
	}
}

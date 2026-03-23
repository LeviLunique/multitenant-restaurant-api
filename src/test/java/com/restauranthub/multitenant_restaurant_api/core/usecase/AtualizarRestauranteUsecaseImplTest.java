package com.restauranthub.multitenant_restaurant_api.core.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

class AtualizarRestauranteUsecaseImplTest {

	private final RestauranteGateway restauranteGateway = Mockito.mock(RestauranteGateway.class);
	private final UsuarioGateway usuarioGateway = Mockito.mock(UsuarioGateway.class);
	private final AtualizarRestauranteUsecaseImpl usecase = new AtualizarRestauranteUsecaseImpl(restauranteGateway, usuarioGateway);

	@Test
	void shouldUpdateRestaurantWhenOwnerIsValid() {
		var restauranteAtualizado = new Restaurante(1L, "Bistrô Atualizado", "Rua B, 200", "Italiana", "10:00-23:00", 11L);
		when(restauranteGateway.obterPorId(1L))
				.thenReturn(Optional.of(new Restaurante(1L, "Bistrô Central", "Rua A, 100", "Francesa", "09:00-22:00", 10L)));
		when(usuarioGateway.obterPorId(11L)).thenReturn(Optional.of(criarUsuarioDono(11L)));
		when(restauranteGateway.atualizar(restauranteAtualizado)).thenReturn(restauranteAtualizado);

		var restaurante = usecase.atualizar(restauranteAtualizado);

		assertEquals(1L, restaurante.getId());
		assertEquals("Bistrô Atualizado", restaurante.getNome());
		verify(restauranteGateway).atualizar(restauranteAtualizado);
	}

	@Test
	void shouldRejectMissingRestaurantDuringUpdate() {
		var restauranteAtualizado = new Restaurante(99L, "Bistrô Atualizado", "Rua B, 200", "Italiana", "10:00-23:00", 11L);
		when(restauranteGateway.obterPorId(99L)).thenReturn(Optional.empty());

		var exception = assertThrows(ResourceNotFoundException.class, () -> usecase.atualizar(restauranteAtualizado));

		assertEquals("RESTAURANT_NOT_FOUND", exception.getCode());
	}

	@Test
	void shouldRejectMissingOwnerDuringUpdate() {
		var restauranteAtualizado = new Restaurante(1L, "Bistrô Atualizado", "Rua B, 200", "Italiana", "10:00-23:00", 11L);
		when(restauranteGateway.obterPorId(1L))
				.thenReturn(Optional.of(new Restaurante(1L, "Bistrô Central", "Rua A, 100", "Francesa", "09:00-22:00", 10L)));
		when(usuarioGateway.obterPorId(11L)).thenReturn(Optional.empty());

		var exception = assertThrows(ResourceNotFoundException.class, () -> usecase.atualizar(restauranteAtualizado));

		assertEquals("RESTAURANT_OWNER_NOT_FOUND", exception.getCode());
		verify(restauranteGateway, Mockito.never()).atualizar(any(Restaurante.class));
	}

	@Test
	void shouldRejectOwnerWithoutOwnerTypeDuringUpdate() {
		var restauranteAtualizado = new Restaurante(1L, "Bistrô Atualizado", "Rua B, 200", "Italiana", "10:00-23:00", 11L);
		var cliente = new Usuario(11L, "Cliente", "cliente@example.com");
		cliente.associarTipoUsuario(new TipoUsuario(1L, "Cliente", TipoUsuarioEnum.CLIENTE));
		when(restauranteGateway.obterPorId(1L))
				.thenReturn(Optional.of(new Restaurante(1L, "Bistrô Central", "Rua A, 100", "Francesa", "09:00-22:00", 10L)));
		when(usuarioGateway.obterPorId(11L)).thenReturn(Optional.of(cliente));

		var exception = assertThrows(BusinessException.class, () -> usecase.atualizar(restauranteAtualizado));

		assertEquals("INVALID_RESTAURANT_OWNER_TYPE", exception.getCode());
		verify(restauranteGateway, Mockito.never()).atualizar(any(Restaurante.class));
	}

	private Usuario criarUsuarioDono(Long id) {
		var dono = new Usuario(id, "Dono", "dono@example.com");
		dono.associarTipoUsuario(new TipoUsuario(2L, "Dono de Restaurante", TipoUsuarioEnum.DONO));
		return dono;
	}
}

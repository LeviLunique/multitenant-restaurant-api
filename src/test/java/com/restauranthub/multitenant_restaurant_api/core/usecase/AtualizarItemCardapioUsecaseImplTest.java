package com.restauranthub.multitenant_restaurant_api.core.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.restauranthub.multitenant_restaurant_api.core.domain.ItemCardapio;
import com.restauranthub.multitenant_restaurant_api.core.domain.Restaurante;
import com.restauranthub.multitenant_restaurant_api.core.exception.ResourceNotFoundException;
import com.restauranthub.multitenant_restaurant_api.core.gateway.ItemCardapioGateway;
import com.restauranthub.multitenant_restaurant_api.core.gateway.RestauranteGateway;

class AtualizarItemCardapioUsecaseImplTest {

	private final ItemCardapioGateway itemCardapioGateway = Mockito.mock(ItemCardapioGateway.class);
	private final RestauranteGateway restauranteGateway = Mockito.mock(RestauranteGateway.class);

	private final AtualizarItemCardapioUsecaseImpl usecase = new AtualizarItemCardapioUsecaseImpl(itemCardapioGateway, restauranteGateway);

	@Test
	void shouldUpdateMenuItemWhenRestaurantAndItemExist() {
		var restaurante = new Restaurante(10L, "Bistro", "Rua A", "Francesa", "09:00-22:00", 1L);
		var itemAtualizado = new ItemCardapio(1L, 10L, "Risoto Atualizado", "Descricao", new BigDecimal("54.90"), false, "/foto.jpg");
		when(restauranteGateway.obterPorId(10L)).thenReturn(Optional.of(restaurante));
		when(itemCardapioGateway.obterPorId(10L, 1L)).thenReturn(Optional.of(
				new ItemCardapio(1L, 10L, "Risoto", "Descricao antiga", new BigDecimal("49.90"), true, "/foto-antiga.jpg")));
		when(itemCardapioGateway.atualizar(itemAtualizado)).thenReturn(itemAtualizado);

		var atualizado = usecase.atualizar(itemAtualizado);

		assertEquals("Risoto Atualizado", atualizado.getNome());
		verify(itemCardapioGateway).atualizar(itemAtualizado);
	}

	@Test
	void shouldRejectUpdateWhenRestaurantDoesNotExist() {
		var itemAtualizado = new ItemCardapio(1L, 10L, "Risoto", "Descricao", new BigDecimal("49.90"), true, "/foto.jpg");
		when(restauranteGateway.obterPorId(10L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> usecase.atualizar(itemAtualizado));
	}

	@Test
	void shouldRejectUpdateWhenItemDoesNotBelongToRestaurant() {
		var restaurante = new Restaurante(10L, "Bistro", "Rua A", "Francesa", "09:00-22:00", 1L);
		var itemAtualizado = new ItemCardapio(1L, 10L, "Risoto", "Descricao", new BigDecimal("49.90"), true, "/foto.jpg");
		when(restauranteGateway.obterPorId(10L)).thenReturn(Optional.of(restaurante));
		when(itemCardapioGateway.obterPorId(10L, 1L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> usecase.atualizar(itemAtualizado));
	}
}

package com.restauranthub.multitenant_restaurant_api.core.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

class BuscarItemCardapioPorIdUsecaseImplTest {

	private final ItemCardapioGateway itemCardapioGateway = Mockito.mock(ItemCardapioGateway.class);
	private final RestauranteGateway restauranteGateway = Mockito.mock(RestauranteGateway.class);

	private final BuscarItemCardapioPorIdUsecaseImpl usecase = new BuscarItemCardapioPorIdUsecaseImpl(itemCardapioGateway, restauranteGateway);

	@Test
	void shouldReturnMenuItemByIdWhenItBelongsToRestaurant() {
		var restaurante = new Restaurante(10L, "Bistro", "Rua A", "Francesa", "09:00-22:00", 1L);
		var itemCardapio = new ItemCardapio(1L, 10L, "Risoto", "Descricao", new BigDecimal("49.90"), true, "/foto.jpg");
		when(restauranteGateway.obterPorId(10L)).thenReturn(Optional.of(restaurante));
		when(itemCardapioGateway.obterPorId(10L, 1L)).thenReturn(Optional.of(itemCardapio));

		var encontrado = usecase.obterPorId(10L, 1L);

		assertEquals(1L, encontrado.getId());
		assertEquals(10L, encontrado.getRestauranteId());
	}

	@Test
	void shouldRejectWhenRestaurantDoesNotExist() {
		when(restauranteGateway.obterPorId(10L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> usecase.obterPorId(10L, 1L));
	}

	@Test
	void shouldRejectWhenMenuItemIsNotInRestaurantContext() {
		var restaurante = new Restaurante(10L, "Bistro", "Rua A", "Francesa", "09:00-22:00", 1L);
		when(restauranteGateway.obterPorId(10L)).thenReturn(Optional.of(restaurante));
		when(itemCardapioGateway.obterPorId(10L, 1L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> usecase.obterPorId(10L, 1L));
	}
}

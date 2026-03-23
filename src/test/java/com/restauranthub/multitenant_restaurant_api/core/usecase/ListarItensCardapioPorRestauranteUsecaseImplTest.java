package com.restauranthub.multitenant_restaurant_api.core.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.restauranthub.multitenant_restaurant_api.core.domain.ItemCardapio;
import com.restauranthub.multitenant_restaurant_api.core.domain.Restaurante;
import com.restauranthub.multitenant_restaurant_api.core.exception.ResourceNotFoundException;
import com.restauranthub.multitenant_restaurant_api.core.gateway.ItemCardapioGateway;
import com.restauranthub.multitenant_restaurant_api.core.gateway.RestauranteGateway;

class ListarItensCardapioPorRestauranteUsecaseImplTest {

	private final ItemCardapioGateway itemCardapioGateway = Mockito.mock(ItemCardapioGateway.class);
	private final RestauranteGateway restauranteGateway = Mockito.mock(RestauranteGateway.class);

	private final ListarItensCardapioPorRestauranteUsecaseImpl usecase = new ListarItensCardapioPorRestauranteUsecaseImpl(
			itemCardapioGateway,
			restauranteGateway);

	@Test
	void shouldListMenuItemsByRestaurant() {
		var restaurante = new Restaurante(10L, "Bistro", "Rua A", "Francesa", "09:00-22:00", 1L);
		when(restauranteGateway.obterPorId(10L)).thenReturn(Optional.of(restaurante));
		when(itemCardapioGateway.listarPorRestaurante(10L)).thenReturn(List.of(
				new ItemCardapio(1L, 10L, "Risoto", "Descricao 1", new BigDecimal("49.90"), true, "/foto-1.jpg"),
				new ItemCardapio(2L, 10L, "Massa", "Descricao 2", new BigDecimal("39.90"), false, "/foto-2.jpg")));

		var itens = usecase.listar(10L);

		assertEquals(2, itens.size());
		verify(itemCardapioGateway).listarPorRestaurante(10L);
	}

	@Test
	void shouldRejectListWhenRestaurantDoesNotExist() {
		when(restauranteGateway.obterPorId(10L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> usecase.listar(10L));
	}
}

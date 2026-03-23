package com.restauranthub.multitenant_restaurant_api.core.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.restauranthub.multitenant_restaurant_api.core.domain.Restaurante;
import com.restauranthub.multitenant_restaurant_api.core.gateway.RestauranteGateway;

class ListarRestaurantesUsecaseImplTest {

	private final RestauranteGateway restauranteGateway = Mockito.mock(RestauranteGateway.class);
	private final ListarRestaurantesUsecaseImpl usecase = new ListarRestaurantesUsecaseImpl(restauranteGateway);

	@Test
	void shouldListRestaurants() {
		when(restauranteGateway.listar()).thenReturn(List.of(
				new Restaurante(1L, "Bistrô Central", "Rua A, 100", "Francesa", "09:00-22:00", 10L),
				new Restaurante(2L, "Cantina Sul", "Rua B, 200", "Italiana", "10:00-23:00", 11L)));

		var restaurantes = usecase.listar();

		assertEquals(2, restaurantes.size());
		assertEquals("Bistrô Central", restaurantes.get(0).getNome());
		assertEquals("Cantina Sul", restaurantes.get(1).getNome());
		verify(restauranteGateway).listar();
	}
}

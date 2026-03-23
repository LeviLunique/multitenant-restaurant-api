package com.restauranthub.multitenant_restaurant_api.core.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.restauranthub.multitenant_restaurant_api.core.domain.Restaurante;
import com.restauranthub.multitenant_restaurant_api.core.exception.ResourceNotFoundException;
import com.restauranthub.multitenant_restaurant_api.core.gateway.RestauranteGateway;

class BuscarRestaurantePorIdUsecaseImplTest {

	private final RestauranteGateway restauranteGateway = Mockito.mock(RestauranteGateway.class);
	private final BuscarRestaurantePorIdUsecaseImpl usecase = new BuscarRestaurantePorIdUsecaseImpl(restauranteGateway);

	@Test
	void shouldReturnRestaurantWhenItExists() {
		when(restauranteGateway.obterPorId(1L))
				.thenReturn(Optional.of(new Restaurante(1L, "Bistrô Central", "Rua A, 100", "Francesa", "09:00-22:00", 10L)));

		var restaurante = usecase.obterPorId(1L);

		assertEquals(1L, restaurante.getId());
		assertEquals("Bistrô Central", restaurante.getNome());
	}

	@Test
	void shouldRejectMissingRestaurant() {
		when(restauranteGateway.obterPorId(99L)).thenReturn(Optional.empty());

		var exception = assertThrows(ResourceNotFoundException.class, () -> usecase.obterPorId(99L));

		assertEquals("RESTAURANT_NOT_FOUND", exception.getCode());
	}
}

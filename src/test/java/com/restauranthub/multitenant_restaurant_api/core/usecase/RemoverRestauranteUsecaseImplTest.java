package com.restauranthub.multitenant_restaurant_api.core.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.restauranthub.multitenant_restaurant_api.core.domain.Restaurante;
import com.restauranthub.multitenant_restaurant_api.core.exception.ResourceNotFoundException;
import com.restauranthub.multitenant_restaurant_api.core.gateway.RestauranteGateway;

class RemoverRestauranteUsecaseImplTest {

	private final RestauranteGateway restauranteGateway = Mockito.mock(RestauranteGateway.class);
	private final RemoverRestauranteUsecaseImpl usecase = new RemoverRestauranteUsecaseImpl(restauranteGateway);

	@Test
	void shouldRemoveExistingRestaurant() {
		when(restauranteGateway.obterPorId(1L))
				.thenReturn(Optional.of(new Restaurante(1L, "Bistrô Central", "Rua A, 100", "Francesa", "09:00-22:00", 10L)));

		usecase.remover(1L);

		verify(restauranteGateway).remover(1L);
	}

	@Test
	void shouldRejectMissingRestaurantDuringRemoval() {
		when(restauranteGateway.obterPorId(99L)).thenReturn(Optional.empty());

		var exception = assertThrows(ResourceNotFoundException.class, () -> usecase.remover(99L));

		assertEquals("RESTAURANT_NOT_FOUND", exception.getCode());
	}
}

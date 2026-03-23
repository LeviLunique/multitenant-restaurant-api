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

class CriarItemCardapioUsecaseImplTest {

	private final ItemCardapioGateway itemCardapioGateway = Mockito.mock(ItemCardapioGateway.class);
	private final RestauranteGateway restauranteGateway = Mockito.mock(RestauranteGateway.class);

	private final CriarItemCardapioUsecaseImpl usecase = new CriarItemCardapioUsecaseImpl(itemCardapioGateway, restauranteGateway);

	@Test
	void shouldCreateMenuItemWhenRestaurantExists() {
		var itemCardapio = new ItemCardapio(null, 10L, "Risoto", "Descricao", new BigDecimal("49.90"), true, "/foto.jpg");
		var restaurante = new Restaurante(10L, "Bistro", "Rua A", "Francesa", "09:00-22:00", 1L);
		when(restauranteGateway.obterPorId(10L)).thenReturn(Optional.of(restaurante));
		when(itemCardapioGateway.criar(itemCardapio)).thenReturn(1L);

		var id = usecase.criar(itemCardapio);

		assertEquals(1L, id);
		verify(itemCardapioGateway).criar(itemCardapio);
	}

	@Test
	void shouldRejectCreationWhenRestaurantDoesNotExist() {
		var itemCardapio = new ItemCardapio(null, 10L, "Risoto", "Descricao", new BigDecimal("49.90"), true, "/foto.jpg");
		when(restauranteGateway.obterPorId(10L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> usecase.criar(itemCardapio));
	}
}

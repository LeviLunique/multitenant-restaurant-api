package com.restauranthub.multitenant_restaurant_api.core.usecase;

import java.util.List;

import com.restauranthub.multitenant_restaurant_api.core.domain.ItemCardapio;
import com.restauranthub.multitenant_restaurant_api.core.exception.ResourceNotFoundException;
import com.restauranthub.multitenant_restaurant_api.core.gateway.ItemCardapioGateway;
import com.restauranthub.multitenant_restaurant_api.core.gateway.RestauranteGateway;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ListarItensCardapioPorRestauranteUsecaseImpl implements ListarItensCardapioPorRestauranteUsecase {

	private static final String MENU_ITEM_RESTAURANT_NOT_FOUND_CODE = "MENU_ITEM_RESTAURANT_NOT_FOUND";
	private static final String MENU_ITEM_RESTAURANT_NOT_FOUND_MESSAGE = "Menu item restaurant not found.";

	private final ItemCardapioGateway itemCardapioGateway;
	private final RestauranteGateway restauranteGateway;

	@Override
	public List<ItemCardapio> listar(Long restauranteId) {
		restauranteGateway.obterPorId(restauranteId)
				.orElseThrow(() -> new ResourceNotFoundException(
						MENU_ITEM_RESTAURANT_NOT_FOUND_CODE,
						MENU_ITEM_RESTAURANT_NOT_FOUND_MESSAGE));
		return itemCardapioGateway.listarPorRestaurante(restauranteId);
	}
}

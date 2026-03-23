package com.restauranthub.multitenant_restaurant_api.core.usecase;

import com.restauranthub.multitenant_restaurant_api.core.domain.ItemCardapio;
import com.restauranthub.multitenant_restaurant_api.core.exception.ResourceNotFoundException;
import com.restauranthub.multitenant_restaurant_api.core.gateway.ItemCardapioGateway;
import com.restauranthub.multitenant_restaurant_api.core.gateway.RestauranteGateway;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BuscarItemCardapioPorIdUsecaseImpl implements BuscarItemCardapioPorIdUsecase {

	private static final String MENU_ITEM_RESTAURANT_NOT_FOUND_CODE = "MENU_ITEM_RESTAURANT_NOT_FOUND";
	private static final String MENU_ITEM_RESTAURANT_NOT_FOUND_MESSAGE = "Menu item restaurant not found.";
	private static final String MENU_ITEM_NOT_FOUND_CODE = "MENU_ITEM_NOT_FOUND";
	private static final String MENU_ITEM_NOT_FOUND_MESSAGE = "Menu item not found.";

	private final ItemCardapioGateway itemCardapioGateway;
	private final RestauranteGateway restauranteGateway;

	@Override
	public ItemCardapio obterPorId(Long restauranteId, Long id) {
		restauranteGateway.obterPorId(restauranteId)
				.orElseThrow(() -> new ResourceNotFoundException(
						MENU_ITEM_RESTAURANT_NOT_FOUND_CODE,
						MENU_ITEM_RESTAURANT_NOT_FOUND_MESSAGE));
		return itemCardapioGateway.obterPorId(restauranteId, id)
				.orElseThrow(() -> new ResourceNotFoundException(MENU_ITEM_NOT_FOUND_CODE, MENU_ITEM_NOT_FOUND_MESSAGE));
	}
}

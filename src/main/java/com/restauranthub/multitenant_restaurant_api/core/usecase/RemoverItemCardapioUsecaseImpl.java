package com.restauranthub.multitenant_restaurant_api.core.usecase;

import com.restauranthub.multitenant_restaurant_api.core.exception.ResourceNotFoundException;
import com.restauranthub.multitenant_restaurant_api.core.gateway.ItemCardapioGateway;
import com.restauranthub.multitenant_restaurant_api.core.gateway.RestauranteGateway;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RemoverItemCardapioUsecaseImpl implements RemoverItemCardapioUsecase {

	private static final String MENU_ITEM_RESTAURANT_NOT_FOUND_CODE = "MENU_ITEM_RESTAURANT_NOT_FOUND";
	private static final String MENU_ITEM_RESTAURANT_NOT_FOUND_MESSAGE = "Menu item restaurant not found.";
	private static final String MENU_ITEM_NOT_FOUND_CODE = "MENU_ITEM_NOT_FOUND";
	private static final String MENU_ITEM_NOT_FOUND_MESSAGE = "Menu item not found.";

	private final ItemCardapioGateway itemCardapioGateway;
	private final RestauranteGateway restauranteGateway;

	@Override
	public void remover(Long restauranteId, Long id) {
		restauranteGateway.obterPorId(restauranteId)
				.orElseThrow(() -> new ResourceNotFoundException(
						MENU_ITEM_RESTAURANT_NOT_FOUND_CODE,
						MENU_ITEM_RESTAURANT_NOT_FOUND_MESSAGE));
		itemCardapioGateway.obterPorId(restauranteId, id)
				.orElseThrow(() -> new ResourceNotFoundException(MENU_ITEM_NOT_FOUND_CODE, MENU_ITEM_NOT_FOUND_MESSAGE));
		itemCardapioGateway.remover(restauranteId, id);
	}
}

package com.restauranthub.multitenant_restaurant_api.core.usecase;

import com.restauranthub.multitenant_restaurant_api.core.exception.ResourceNotFoundException;
import com.restauranthub.multitenant_restaurant_api.core.gateway.RestauranteGateway;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RemoverRestauranteUsecaseImpl implements RemoverRestauranteUsecase {

	private static final String RESTAURANT_NOT_FOUND_CODE = "RESTAURANT_NOT_FOUND";
	private static final String RESTAURANT_NOT_FOUND_MESSAGE = "Restaurant not found.";

	private final RestauranteGateway restauranteGateway;

	@Override
	public void remover(Long id) {
		restauranteGateway.obterPorId(id)
				.orElseThrow(() -> new ResourceNotFoundException(RESTAURANT_NOT_FOUND_CODE, RESTAURANT_NOT_FOUND_MESSAGE));
		restauranteGateway.remover(id);
	}
}

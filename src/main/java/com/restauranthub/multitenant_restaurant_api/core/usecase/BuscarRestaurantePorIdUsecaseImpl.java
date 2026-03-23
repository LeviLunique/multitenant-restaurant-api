package com.restauranthub.multitenant_restaurant_api.core.usecase;

import com.restauranthub.multitenant_restaurant_api.core.domain.Restaurante;
import com.restauranthub.multitenant_restaurant_api.core.exception.ResourceNotFoundException;
import com.restauranthub.multitenant_restaurant_api.core.gateway.RestauranteGateway;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BuscarRestaurantePorIdUsecaseImpl implements BuscarRestaurantePorIdUsecase {

	private static final String RESTAURANT_NOT_FOUND_CODE = "RESTAURANT_NOT_FOUND";
	private static final String RESTAURANT_NOT_FOUND_MESSAGE = "Restaurant not found.";

	private final RestauranteGateway restauranteGateway;

	@Override
	public Restaurante obterPorId(Long id) {
		return restauranteGateway.obterPorId(id)
				.orElseThrow(() -> new ResourceNotFoundException(RESTAURANT_NOT_FOUND_CODE, RESTAURANT_NOT_FOUND_MESSAGE));
	}
}

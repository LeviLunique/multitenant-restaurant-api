package com.restauranthub.multitenant_restaurant_api.core.usecase;

import java.util.List;

import com.restauranthub.multitenant_restaurant_api.core.domain.Restaurante;
import com.restauranthub.multitenant_restaurant_api.core.gateway.RestauranteGateway;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ListarRestaurantesUsecaseImpl implements ListarRestaurantesUsecase {

	private final RestauranteGateway restauranteGateway;

	@Override
	public List<Restaurante> listar() {
		return restauranteGateway.listar();
	}
}

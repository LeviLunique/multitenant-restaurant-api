package com.restauranthub.multitenant_restaurant_api.core.usecase;

import java.util.List;

import com.restauranthub.multitenant_restaurant_api.core.domain.Restaurante;

public interface ListarRestaurantesUsecase {

	List<Restaurante> listar();
}

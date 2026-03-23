package com.restauranthub.multitenant_restaurant_api.core.usecase;

import com.restauranthub.multitenant_restaurant_api.core.domain.Restaurante;

public interface CriarRestauranteUsecase {

	Long criar(Restaurante restaurante);
}

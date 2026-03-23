package com.restauranthub.multitenant_restaurant_api.core.usecase;

import com.restauranthub.multitenant_restaurant_api.core.domain.ItemCardapio;

public interface BuscarItemCardapioPorIdUsecase {

	ItemCardapio obterPorId(Long restauranteId, Long id);
}

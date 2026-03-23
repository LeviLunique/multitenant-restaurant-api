package com.restauranthub.multitenant_restaurant_api.core.usecase;

import java.util.List;

import com.restauranthub.multitenant_restaurant_api.core.domain.ItemCardapio;

public interface ListarItensCardapioPorRestauranteUsecase {

	List<ItemCardapio> listar(Long restauranteId);
}

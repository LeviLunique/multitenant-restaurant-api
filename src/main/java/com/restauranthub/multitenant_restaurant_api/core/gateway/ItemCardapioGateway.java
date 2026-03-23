package com.restauranthub.multitenant_restaurant_api.core.gateway;

import java.util.List;
import java.util.Optional;

import com.restauranthub.multitenant_restaurant_api.core.domain.ItemCardapio;

public interface ItemCardapioGateway {

	Long criar(ItemCardapio itemCardapio);

	Optional<ItemCardapio> obterPorId(Long restauranteId, Long id);

	List<ItemCardapio> listarPorRestaurante(Long restauranteId);

	ItemCardapio atualizar(ItemCardapio itemCardapio);

	void remover(Long restauranteId, Long id);
}

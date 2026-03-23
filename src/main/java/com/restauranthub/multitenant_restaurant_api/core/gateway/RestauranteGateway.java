package com.restauranthub.multitenant_restaurant_api.core.gateway;

import java.util.List;
import java.util.Optional;

import com.restauranthub.multitenant_restaurant_api.core.domain.Restaurante;

public interface RestauranteGateway {

	Long criar(Restaurante restaurante);

	Optional<Restaurante> obterPorId(Long id);

	List<Restaurante> listar();

	Restaurante atualizar(Restaurante restaurante);

	void remover(Long id);
}

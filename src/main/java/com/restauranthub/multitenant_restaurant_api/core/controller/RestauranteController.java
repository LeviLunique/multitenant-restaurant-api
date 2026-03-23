package com.restauranthub.multitenant_restaurant_api.core.controller;

import java.util.List;

import com.restauranthub.multitenant_restaurant_api.core.dto.AtualizarRestauranteInputDto;
import com.restauranthub.multitenant_restaurant_api.core.dto.CriarRestauranteInputDto;
import com.restauranthub.multitenant_restaurant_api.core.dto.RestauranteOutputDto;
import com.restauranthub.multitenant_restaurant_api.core.mapper.RestauranteMapper;
import com.restauranthub.multitenant_restaurant_api.core.usecase.AtualizarRestauranteUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.BuscarRestaurantePorIdUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.CriarRestauranteUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.ListarRestaurantesUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.RemoverRestauranteUsecase;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestauranteController {

	private final CriarRestauranteUsecase criarRestauranteUsecase;
	private final BuscarRestaurantePorIdUsecase buscarRestaurantePorIdUsecase;
	private final ListarRestaurantesUsecase listarRestaurantesUsecase;
	private final AtualizarRestauranteUsecase atualizarRestauranteUsecase;
	private final RemoverRestauranteUsecase removerRestauranteUsecase;
	private final RestauranteMapper restauranteMapper;

	public Long criar(CriarRestauranteInputDto criarRestauranteInputDto) {
		return criarRestauranteUsecase.criar(restauranteMapper.map(criarRestauranteInputDto));
	}

	public RestauranteOutputDto obterPorId(Long id) {
		return restauranteMapper.map(buscarRestaurantePorIdUsecase.obterPorId(id));
	}

	public List<RestauranteOutputDto> listar() {
		return restauranteMapper.map(listarRestaurantesUsecase.listar());
	}

	public RestauranteOutputDto atualizar(Long id, AtualizarRestauranteInputDto atualizarRestauranteInputDto) {
		return restauranteMapper.map(atualizarRestauranteUsecase.atualizar(restauranteMapper.map(id, atualizarRestauranteInputDto)));
	}

	public void remover(Long id) {
		removerRestauranteUsecase.remover(id);
	}
}

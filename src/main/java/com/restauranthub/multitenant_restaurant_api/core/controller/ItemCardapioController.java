package com.restauranthub.multitenant_restaurant_api.core.controller;

import java.util.List;

import com.restauranthub.multitenant_restaurant_api.core.dto.AtualizarItemCardapioInputDto;
import com.restauranthub.multitenant_restaurant_api.core.dto.CriarItemCardapioInputDto;
import com.restauranthub.multitenant_restaurant_api.core.dto.ItemCardapioOutputDto;
import com.restauranthub.multitenant_restaurant_api.core.mapper.ItemCardapioMapper;
import com.restauranthub.multitenant_restaurant_api.core.usecase.AtualizarItemCardapioUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.BuscarItemCardapioPorIdUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.CriarItemCardapioUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.ListarItensCardapioPorRestauranteUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.RemoverItemCardapioUsecase;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ItemCardapioController {

	private final CriarItemCardapioUsecase criarItemCardapioUsecase;
	private final BuscarItemCardapioPorIdUsecase buscarItemCardapioPorIdUsecase;
	private final ListarItensCardapioPorRestauranteUsecase listarItensCardapioPorRestauranteUsecase;
	private final AtualizarItemCardapioUsecase atualizarItemCardapioUsecase;
	private final RemoverItemCardapioUsecase removerItemCardapioUsecase;
	private final ItemCardapioMapper itemCardapioMapper;

	public Long criar(Long restauranteId, CriarItemCardapioInputDto criarItemCardapioInputDto) {
		return criarItemCardapioUsecase.criar(itemCardapioMapper.map(restauranteId, criarItemCardapioInputDto));
	}

	public ItemCardapioOutputDto obterPorId(Long restauranteId, Long id) {
		return itemCardapioMapper.map(buscarItemCardapioPorIdUsecase.obterPorId(restauranteId, id));
	}

	public List<ItemCardapioOutputDto> listar(Long restauranteId) {
		return itemCardapioMapper.map(listarItensCardapioPorRestauranteUsecase.listar(restauranteId));
	}

	public ItemCardapioOutputDto atualizar(
			Long restauranteId,
			Long id,
			AtualizarItemCardapioInputDto atualizarItemCardapioInputDto) {
		return itemCardapioMapper.map(atualizarItemCardapioUsecase.atualizar(
				itemCardapioMapper.map(restauranteId, id, atualizarItemCardapioInputDto)));
	}

	public void remover(Long restauranteId, Long id) {
		removerItemCardapioUsecase.remover(restauranteId, id);
	}
}

package com.restauranthub.multitenant_restaurant_api.core.mapper;

import java.util.List;

import com.restauranthub.multitenant_restaurant_api.core.domain.ItemCardapio;
import com.restauranthub.multitenant_restaurant_api.core.dto.AtualizarItemCardapioInputDto;
import com.restauranthub.multitenant_restaurant_api.core.dto.CriarItemCardapioInputDto;
import com.restauranthub.multitenant_restaurant_api.core.dto.ItemCardapioOutputDto;

public class ItemCardapioMapper {

	public ItemCardapio map(Long restauranteId, CriarItemCardapioInputDto criarItemCardapioInputDto) {
		return new ItemCardapio(
				null,
				restauranteId,
				criarItemCardapioInputDto.nome(),
				criarItemCardapioInputDto.descricao(),
				criarItemCardapioInputDto.preco(),
				criarItemCardapioInputDto.apenasConsumoNoLocal(),
				criarItemCardapioInputDto.caminhoFoto());
	}

	public ItemCardapio map(Long restauranteId, Long id, AtualizarItemCardapioInputDto atualizarItemCardapioInputDto) {
		return new ItemCardapio(
				id,
				restauranteId,
				atualizarItemCardapioInputDto.nome(),
				atualizarItemCardapioInputDto.descricao(),
				atualizarItemCardapioInputDto.preco(),
				atualizarItemCardapioInputDto.apenasConsumoNoLocal(),
				atualizarItemCardapioInputDto.caminhoFoto());
	}

	public ItemCardapioOutputDto map(ItemCardapio itemCardapio) {
		return new ItemCardapioOutputDto(
				itemCardapio.getId(),
				itemCardapio.getRestauranteId(),
				itemCardapio.getNome(),
				itemCardapio.getDescricao(),
				itemCardapio.getPreco(),
				itemCardapio.getApenasConsumoNoLocal(),
				itemCardapio.getCaminhoFoto());
	}

	public List<ItemCardapioOutputDto> map(List<ItemCardapio> itensCardapio) {
		return itensCardapio.stream().map(this::map).toList();
	}
}

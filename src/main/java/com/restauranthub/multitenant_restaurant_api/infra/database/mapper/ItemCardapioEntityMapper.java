package com.restauranthub.multitenant_restaurant_api.infra.database.mapper;

import org.springframework.stereotype.Component;

import com.restauranthub.multitenant_restaurant_api.core.domain.ItemCardapio;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.entity.ItemCardapioEntity;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.entity.RestauranteEntity;

@Component
public class ItemCardapioEntityMapper {

	public ItemCardapio map(ItemCardapioEntity entity) {
		return new ItemCardapio(
				entity.getId(),
				entity.getRestaurante().getId(),
				entity.getNome(),
				entity.getDescricao(),
				entity.getPreco(),
				entity.getApenasConsumoNoLocal(),
				entity.getCaminhoFoto());
	}

	public ItemCardapioEntity map(ItemCardapio itemCardapio) {
		return new ItemCardapioEntity(
				itemCardapio.getId(),
				itemCardapio.getNome(),
				itemCardapio.getDescricao(),
				itemCardapio.getPreco(),
				itemCardapio.getApenasConsumoNoLocal(),
				itemCardapio.getCaminhoFoto(),
				new RestauranteEntity(itemCardapio.getRestauranteId(), null, null, null, null, null));
	}
}

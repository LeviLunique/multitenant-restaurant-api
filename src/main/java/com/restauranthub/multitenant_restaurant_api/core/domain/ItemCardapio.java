package com.restauranthub.multitenant_restaurant_api.core.domain;

import java.math.BigDecimal;

import com.restauranthub.multitenant_restaurant_api.core.exception.BusinessException;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ItemCardapio {

	private static final String INVALID_MENU_ITEM_NAME_CODE = "INVALID_MENU_ITEM_NAME";
	private static final String INVALID_MENU_ITEM_NAME_MESSAGE = "Menu item name must be provided.";
	private static final String INVALID_MENU_ITEM_DESCRIPTION_CODE = "INVALID_MENU_ITEM_DESCRIPTION";
	private static final String INVALID_MENU_ITEM_DESCRIPTION_MESSAGE = "Menu item description must be provided.";
	private static final String INVALID_MENU_ITEM_PRICE_CODE = "INVALID_MENU_ITEM_PRICE";
	private static final String INVALID_MENU_ITEM_PRICE_MESSAGE = "Menu item price must be greater than zero.";
	private static final String INVALID_MENU_ITEM_LOCAL_CONSUMPTION_CODE = "INVALID_MENU_ITEM_LOCAL_CONSUMPTION";
	private static final String INVALID_MENU_ITEM_LOCAL_CONSUMPTION_MESSAGE = "Menu item local consumption availability must be provided.";
	private static final String INVALID_MENU_ITEM_PHOTO_PATH_CODE = "INVALID_MENU_ITEM_PHOTO_PATH";
	private static final String INVALID_MENU_ITEM_PHOTO_PATH_MESSAGE = "Menu item photo path must be provided.";
	private static final String INVALID_MENU_ITEM_RESTAURANT_CODE = "INVALID_MENU_ITEM_RESTAURANT";
	private static final String INVALID_MENU_ITEM_RESTAURANT_MESSAGE = "Menu item restaurant must be provided.";

	private final Long id;
	private final Long restauranteId;
	private final String nome;
	private final String descricao;
	private final BigDecimal preco;
	private final Boolean apenasConsumoNoLocal;
	private final String caminhoFoto;

	public ItemCardapio(
			Long id,
			Long restauranteId,
			String nome,
			String descricao,
			BigDecimal preco,
			Boolean apenasConsumoNoLocal,
			String caminhoFoto) {
		validarRestauranteId(restauranteId);
		validarNome(nome);
		validarDescricao(descricao);
		validarPreco(preco);
		validarApenasConsumoNoLocal(apenasConsumoNoLocal);
		validarCaminhoFoto(caminhoFoto);
		this.id = id;
		this.restauranteId = restauranteId;
		this.nome = nome.trim();
		this.descricao = descricao.trim();
		this.preco = preco;
		this.apenasConsumoNoLocal = apenasConsumoNoLocal;
		this.caminhoFoto = caminhoFoto.trim();
	}

	private void validarRestauranteId(Long restauranteId) {
		if (restauranteId == null || restauranteId <= 0L) {
			throw new BusinessException(INVALID_MENU_ITEM_RESTAURANT_CODE, INVALID_MENU_ITEM_RESTAURANT_MESSAGE);
		}
	}

	private void validarNome(String nome) {
		if (nome == null || nome.isBlank()) {
			throw new BusinessException(INVALID_MENU_ITEM_NAME_CODE, INVALID_MENU_ITEM_NAME_MESSAGE);
		}
	}

	private void validarDescricao(String descricao) {
		if (descricao == null || descricao.isBlank()) {
			throw new BusinessException(INVALID_MENU_ITEM_DESCRIPTION_CODE, INVALID_MENU_ITEM_DESCRIPTION_MESSAGE);
		}
	}

	private void validarPreco(BigDecimal preco) {
		if (preco == null || preco.signum() <= 0) {
			throw new BusinessException(INVALID_MENU_ITEM_PRICE_CODE, INVALID_MENU_ITEM_PRICE_MESSAGE);
		}
	}

	private void validarApenasConsumoNoLocal(Boolean apenasConsumoNoLocal) {
		if (apenasConsumoNoLocal == null) {
			throw new BusinessException(INVALID_MENU_ITEM_LOCAL_CONSUMPTION_CODE, INVALID_MENU_ITEM_LOCAL_CONSUMPTION_MESSAGE);
		}
	}

	private void validarCaminhoFoto(String caminhoFoto) {
		if (caminhoFoto == null || caminhoFoto.isBlank()) {
			throw new BusinessException(INVALID_MENU_ITEM_PHOTO_PATH_CODE, INVALID_MENU_ITEM_PHOTO_PATH_MESSAGE);
		}
	}
}

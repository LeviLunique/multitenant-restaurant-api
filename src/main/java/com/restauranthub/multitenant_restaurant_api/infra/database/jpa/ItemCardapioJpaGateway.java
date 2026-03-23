package com.restauranthub.multitenant_restaurant_api.infra.database.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.restauranthub.multitenant_restaurant_api.core.domain.ItemCardapio;
import com.restauranthub.multitenant_restaurant_api.core.exception.InfrastructureException;
import com.restauranthub.multitenant_restaurant_api.core.gateway.ItemCardapioGateway;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.repository.ItemCardapioRepository;
import com.restauranthub.multitenant_restaurant_api.infra.database.mapper.ItemCardapioEntityMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ItemCardapioJpaGateway implements ItemCardapioGateway {

	private static final String MENU_ITEM_REPOSITORY_ERROR_CODE = "MENU_ITEM_REPOSITORY_ERROR";
	private static final String COULD_NOT_PERSIST_MENU_ITEM_MESSAGE = "Could not persist menu item.";
	private static final String COULD_NOT_QUERY_MENU_ITEM_MESSAGE = "Could not query menu item.";
	private static final String COULD_NOT_LIST_MENU_ITEMS_MESSAGE = "Could not list menu items.";
	private static final String COULD_NOT_UPDATE_MENU_ITEM_MESSAGE = "Could not update menu item.";
	private static final String COULD_NOT_REMOVE_MENU_ITEM_MESSAGE = "Could not remove menu item.";

	private final ItemCardapioRepository itemCardapioRepository;
	private final ItemCardapioEntityMapper itemCardapioEntityMapper;

	@Override
	public Long criar(ItemCardapio itemCardapio) {
		try {
			return itemCardapioRepository.save(itemCardapioEntityMapper.map(itemCardapio)).getId();
		} catch (Exception exception) {
			throw new InfrastructureException(MENU_ITEM_REPOSITORY_ERROR_CODE, COULD_NOT_PERSIST_MENU_ITEM_MESSAGE);
		}
	}

	@Override
	public Optional<ItemCardapio> obterPorId(Long restauranteId, Long id) {
		try {
			return itemCardapioRepository.findByIdAndRestauranteId(id, restauranteId).map(itemCardapioEntityMapper::map);
		} catch (Exception exception) {
			throw new InfrastructureException(MENU_ITEM_REPOSITORY_ERROR_CODE, COULD_NOT_QUERY_MENU_ITEM_MESSAGE);
		}
	}

	@Override
	public List<ItemCardapio> listarPorRestaurante(Long restauranteId) {
		try {
			return itemCardapioRepository.findAllByRestauranteIdOrderByIdAsc(restauranteId).stream().map(itemCardapioEntityMapper::map).toList();
		} catch (Exception exception) {
			throw new InfrastructureException(MENU_ITEM_REPOSITORY_ERROR_CODE, COULD_NOT_LIST_MENU_ITEMS_MESSAGE);
		}
	}

	@Override
	public ItemCardapio atualizar(ItemCardapio itemCardapio) {
		try {
			return itemCardapioEntityMapper.map(itemCardapioRepository.save(itemCardapioEntityMapper.map(itemCardapio)));
		} catch (Exception exception) {
			throw new InfrastructureException(MENU_ITEM_REPOSITORY_ERROR_CODE, COULD_NOT_UPDATE_MENU_ITEM_MESSAGE);
		}
	}

	@Override
	public void remover(Long restauranteId, Long id) {
		try {
			itemCardapioRepository.findByIdAndRestauranteId(id, restauranteId).ifPresent(itemCardapioRepository::delete);
		} catch (Exception exception) {
			throw new InfrastructureException(MENU_ITEM_REPOSITORY_ERROR_CODE, COULD_NOT_REMOVE_MENU_ITEM_MESSAGE);
		}
	}
}

package com.restauranthub.multitenant_restaurant_api.infra.database.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.restauranthub.multitenant_restaurant_api.core.domain.Restaurante;
import com.restauranthub.multitenant_restaurant_api.core.exception.InfrastructureException;
import com.restauranthub.multitenant_restaurant_api.core.gateway.RestauranteGateway;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.repository.RestauranteRepository;
import com.restauranthub.multitenant_restaurant_api.infra.database.mapper.RestauranteEntityMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RestauranteJpaGateway implements RestauranteGateway {

	private static final String RESTAURANT_REPOSITORY_ERROR_CODE = "RESTAURANT_REPOSITORY_ERROR";
	private static final String COULD_NOT_PERSIST_RESTAURANT_MESSAGE = "Could not persist restaurant.";
	private static final String COULD_NOT_QUERY_RESTAURANT_BY_ID_MESSAGE = "Could not query restaurant by id.";
	private static final String COULD_NOT_LIST_RESTAURANTS_MESSAGE = "Could not list restaurants.";
	private static final String COULD_NOT_UPDATE_RESTAURANT_MESSAGE = "Could not update restaurant.";
	private static final String COULD_NOT_REMOVE_RESTAURANT_MESSAGE = "Could not remove restaurant.";

	private final RestauranteRepository restauranteRepository;
	private final RestauranteEntityMapper restauranteEntityMapper;

	@Override
	public Long criar(Restaurante restaurante) {
		try {
			return restauranteRepository.save(restauranteEntityMapper.map(restaurante)).getId();
		} catch (Exception exception) {
			throw new InfrastructureException(RESTAURANT_REPOSITORY_ERROR_CODE, COULD_NOT_PERSIST_RESTAURANT_MESSAGE);
		}
	}

	@Override
	public Optional<Restaurante> obterPorId(Long id) {
		try {
			return restauranteRepository.findById(id).map(restauranteEntityMapper::map);
		} catch (Exception exception) {
			throw new InfrastructureException(RESTAURANT_REPOSITORY_ERROR_CODE, COULD_NOT_QUERY_RESTAURANT_BY_ID_MESSAGE);
		}
	}

	@Override
	public List<Restaurante> listar() {
		try {
			return restauranteRepository.findAllByOrderByIdAsc().stream().map(restauranteEntityMapper::map).toList();
		} catch (Exception exception) {
			throw new InfrastructureException(RESTAURANT_REPOSITORY_ERROR_CODE, COULD_NOT_LIST_RESTAURANTS_MESSAGE);
		}
	}

	@Override
	public Restaurante atualizar(Restaurante restaurante) {
		try {
			return restauranteEntityMapper.map(restauranteRepository.save(restauranteEntityMapper.map(restaurante)));
		} catch (Exception exception) {
			throw new InfrastructureException(RESTAURANT_REPOSITORY_ERROR_CODE, COULD_NOT_UPDATE_RESTAURANT_MESSAGE);
		}
	}

	@Override
	public void remover(Long id) {
		try {
			restauranteRepository.deleteById(id);
		} catch (Exception exception) {
			throw new InfrastructureException(RESTAURANT_REPOSITORY_ERROR_CODE, COULD_NOT_REMOVE_RESTAURANT_MESSAGE);
		}
	}
}

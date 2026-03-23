package com.restauranthub.multitenant_restaurant_api.infra.database.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.restauranthub.multitenant_restaurant_api.core.domain.Restaurante;
import com.restauranthub.multitenant_restaurant_api.core.exception.InfrastructureException;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.entity.RestauranteEntity;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.repository.RestauranteRepository;
import com.restauranthub.multitenant_restaurant_api.infra.database.mapper.RestauranteEntityMapper;

@ExtendWith(MockitoExtension.class)
class RestauranteJpaGatewayTest {

	private static final String ERROR_CODE = "RESTAURANT_REPOSITORY_ERROR";

	@Mock
	private RestauranteRepository restauranteRepository;

	private RestauranteJpaGateway gateway;

	@BeforeEach
	void setUp() {
		gateway = new RestauranteJpaGateway(restauranteRepository, new RestauranteEntityMapper());
	}

	@Test
	void shouldReturnEmptyWhenRestaurantIsNotFoundById() {
		when(restauranteRepository.findById(99L)).thenReturn(Optional.empty());

		var resultado = gateway.obterPorId(99L);

		assertTrue(resultado.isEmpty());
	}

	@Test
	void shouldWrapRepositoryErrorWhenCreatingRestaurant() {
		var restaurante = new Restaurante(null, "Bistro", "Rua A", "Francesa", "09:00-18:00", 1L);

		when(restauranteRepository.save(any(RestauranteEntity.class))).thenThrow(new RuntimeException("boom"));

		var exception = assertThrows(InfrastructureException.class, () -> gateway.criar(restaurante));

		assertEquals(ERROR_CODE, exception.getCode());
		assertEquals("Could not persist restaurant.", exception.getMessage());
	}

	@Test
	void shouldWrapRepositoryErrorWhenFindingRestaurantById() {
		when(restauranteRepository.findById(1L)).thenThrow(new RuntimeException("boom"));

		var exception = assertThrows(InfrastructureException.class, () -> gateway.obterPorId(1L));

		assertEquals(ERROR_CODE, exception.getCode());
		assertEquals("Could not query restaurant by id.", exception.getMessage());
	}

	@Test
	void shouldWrapRepositoryErrorWhenListingRestaurants() {
		when(restauranteRepository.findAllByOrderByIdAsc()).thenThrow(new RuntimeException("boom"));

		var exception = assertThrows(InfrastructureException.class, gateway::listar);

		assertEquals(ERROR_CODE, exception.getCode());
		assertEquals("Could not list restaurants.", exception.getMessage());
	}

	@Test
	void shouldWrapRepositoryErrorWhenUpdatingRestaurant() {
		var restaurante = new Restaurante(1L, "Bistro", "Rua A", "Francesa", "09:00-18:00", 1L);

		when(restauranteRepository.save(any(RestauranteEntity.class))).thenThrow(new RuntimeException("boom"));

		var exception = assertThrows(InfrastructureException.class, () -> gateway.atualizar(restaurante));

		assertEquals(ERROR_CODE, exception.getCode());
		assertEquals("Could not update restaurant.", exception.getMessage());
	}

	@Test
	void shouldWrapRepositoryErrorWhenRemovingRestaurant() {
		doThrow(new RuntimeException("boom")).when(restauranteRepository).deleteById(1L);

		var exception = assertThrows(InfrastructureException.class, () -> gateway.remover(1L));

		assertEquals(ERROR_CODE, exception.getCode());
		assertEquals("Could not remove restaurant.", exception.getMessage());
	}
}

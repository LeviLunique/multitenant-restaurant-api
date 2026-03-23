package com.restauranthub.multitenant_restaurant_api.infra.database.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.restauranthub.multitenant_restaurant_api.core.domain.ItemCardapio;
import com.restauranthub.multitenant_restaurant_api.core.exception.InfrastructureException;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.entity.ItemCardapioEntity;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.repository.ItemCardapioRepository;
import com.restauranthub.multitenant_restaurant_api.infra.database.mapper.ItemCardapioEntityMapper;

@ExtendWith(MockitoExtension.class)
class ItemCardapioJpaGatewayTest {

	private static final String ERROR_CODE = "MENU_ITEM_REPOSITORY_ERROR";

	@Mock
	private ItemCardapioRepository itemCardapioRepository;

	private ItemCardapioJpaGateway gateway;

	@BeforeEach
	void setUp() {
		gateway = new ItemCardapioJpaGateway(itemCardapioRepository, new ItemCardapioEntityMapper());
	}

	@Test
	void shouldNotDeleteMenuItemWhenRestaurantScopedItemIsMissing() {
		when(itemCardapioRepository.findByIdAndRestauranteId(2L, 1L)).thenReturn(Optional.empty());

		gateway.remover(1L, 2L);

		verify(itemCardapioRepository, never()).delete(any(ItemCardapioEntity.class));
	}

	@Test
	void shouldWrapRepositoryErrorWhenCreatingMenuItem() {
		var item = new ItemCardapio(null, 1L, "Risoto", "Descricao", new BigDecimal("49.90"), true, "/foto.jpg");

		when(itemCardapioRepository.save(any(ItemCardapioEntity.class))).thenThrow(new RuntimeException("boom"));

		var exception = assertThrows(InfrastructureException.class, () -> gateway.criar(item));

		assertEquals(ERROR_CODE, exception.getCode());
		assertEquals("Could not persist menu item.", exception.getMessage());
	}

	@Test
	void shouldWrapRepositoryErrorWhenFindingMenuItem() {
		when(itemCardapioRepository.findByIdAndRestauranteId(2L, 1L)).thenThrow(new RuntimeException("boom"));

		var exception = assertThrows(InfrastructureException.class, () -> gateway.obterPorId(1L, 2L));

		assertEquals(ERROR_CODE, exception.getCode());
		assertEquals("Could not query menu item.", exception.getMessage());
	}

	@Test
	void shouldWrapRepositoryErrorWhenListingMenuItems() {
		when(itemCardapioRepository.findAllByRestauranteIdOrderByIdAsc(1L)).thenThrow(new RuntimeException("boom"));

		var exception = assertThrows(InfrastructureException.class, () -> gateway.listarPorRestaurante(1L));

		assertEquals(ERROR_CODE, exception.getCode());
		assertEquals("Could not list menu items.", exception.getMessage());
	}

	@Test
	void shouldWrapRepositoryErrorWhenUpdatingMenuItem() {
		var item = new ItemCardapio(2L, 1L, "Risoto", "Descricao", new BigDecimal("49.90"), true, "/foto.jpg");

		when(itemCardapioRepository.save(any(ItemCardapioEntity.class))).thenThrow(new RuntimeException("boom"));

		var exception = assertThrows(InfrastructureException.class, () -> gateway.atualizar(item));

		assertEquals(ERROR_CODE, exception.getCode());
		assertEquals("Could not update menu item.", exception.getMessage());
	}

	@Test
	void shouldWrapRepositoryErrorWhenRemovingMenuItem() {
		when(itemCardapioRepository.findByIdAndRestauranteId(2L, 1L)).thenThrow(new RuntimeException("boom"));

		var exception = assertThrows(InfrastructureException.class, () -> gateway.remover(1L, 2L));

		assertEquals(ERROR_CODE, exception.getCode());
		assertEquals("Could not remove menu item.", exception.getMessage());
	}
}

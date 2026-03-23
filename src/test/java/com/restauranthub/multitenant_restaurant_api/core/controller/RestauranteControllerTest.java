package com.restauranthub.multitenant_restaurant_api.core.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.restauranthub.multitenant_restaurant_api.core.domain.Restaurante;
import com.restauranthub.multitenant_restaurant_api.core.dto.AtualizarRestauranteInputDto;
import com.restauranthub.multitenant_restaurant_api.core.dto.CriarRestauranteInputDto;
import com.restauranthub.multitenant_restaurant_api.core.mapper.RestauranteMapper;
import com.restauranthub.multitenant_restaurant_api.core.usecase.AtualizarRestauranteUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.BuscarRestaurantePorIdUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.CriarRestauranteUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.ListarRestaurantesUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.RemoverRestauranteUsecase;

class RestauranteControllerTest {

	private final CriarRestauranteUsecase criarRestauranteUsecase = Mockito.mock(CriarRestauranteUsecase.class);
	private final BuscarRestaurantePorIdUsecase buscarRestaurantePorIdUsecase = Mockito.mock(BuscarRestaurantePorIdUsecase.class);
	private final ListarRestaurantesUsecase listarRestaurantesUsecase = Mockito.mock(ListarRestaurantesUsecase.class);
	private final AtualizarRestauranteUsecase atualizarRestauranteUsecase = Mockito.mock(AtualizarRestauranteUsecase.class);
	private final RemoverRestauranteUsecase removerRestauranteUsecase = Mockito.mock(RemoverRestauranteUsecase.class);
	private final RestauranteMapper mapper = new RestauranteMapper();

	private final RestauranteController controller = new RestauranteController(
			criarRestauranteUsecase,
			buscarRestaurantePorIdUsecase,
			listarRestaurantesUsecase,
			atualizarRestauranteUsecase,
			removerRestauranteUsecase,
			mapper);

	@Test
	void shouldCreateRestaurant() {
		var dto = new CriarRestauranteInputDto("Bistrô Central", "Rua A, 100", "Francesa", "09:00-22:00", 10L);
		when(criarRestauranteUsecase.criar(new Restaurante(null, "Bistrô Central", "Rua A, 100", "Francesa", "09:00-22:00", 10L)))
				.thenReturn(1L);

		var id = controller.criar(dto);

		assertEquals(1L, id);
	}

	@Test
	void shouldReturnRestaurantById() {
		when(buscarRestaurantePorIdUsecase.obterPorId(1L))
				.thenReturn(new Restaurante(1L, "Bistrô Central", "Rua A, 100", "Francesa", "09:00-22:00", 10L));

		var output = controller.obterPorId(1L);

		assertEquals(1L, output.id());
		assertEquals("Bistrô Central", output.nome());
		assertEquals(10L, output.donoUsuarioId());
	}

	@Test
	void shouldListRestaurants() {
		when(listarRestaurantesUsecase.listar()).thenReturn(List.of(
				new Restaurante(1L, "Bistrô Central", "Rua A, 100", "Francesa", "09:00-22:00", 10L),
				new Restaurante(2L, "Cantina Sul", "Rua B, 200", "Italiana", "10:00-23:00", 11L)));

		var output = controller.listar();

		assertEquals(2, output.size());
		assertEquals("Bistrô Central", output.get(0).nome());
		assertEquals("Cantina Sul", output.get(1).nome());
		verify(listarRestaurantesUsecase).listar();
	}

	@Test
	void shouldUpdateRestaurant() {
		when(atualizarRestauranteUsecase.atualizar(new Restaurante(1L, "Bistrô Atualizado", "Rua B, 200", "Italiana", "10:00-23:00", 11L)))
				.thenReturn(new Restaurante(1L, "Bistrô Atualizado", "Rua B, 200", "Italiana", "10:00-23:00", 11L));

		var output = controller.atualizar(1L, new AtualizarRestauranteInputDto("Bistrô Atualizado", "Rua B, 200", "Italiana", "10:00-23:00", 11L));

		assertEquals(1L, output.id());
		assertEquals("Bistrô Atualizado", output.nome());
		assertEquals(11L, output.donoUsuarioId());
	}

	@Test
	void shouldRemoveRestaurant() {
		controller.remover(1L);

		verify(removerRestauranteUsecase).remover(1L);
	}
}

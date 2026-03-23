package com.restauranthub.multitenant_restaurant_api.core.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.restauranthub.multitenant_restaurant_api.core.domain.ItemCardapio;
import com.restauranthub.multitenant_restaurant_api.core.dto.AtualizarItemCardapioInputDto;
import com.restauranthub.multitenant_restaurant_api.core.dto.CriarItemCardapioInputDto;
import com.restauranthub.multitenant_restaurant_api.core.mapper.ItemCardapioMapper;
import com.restauranthub.multitenant_restaurant_api.core.usecase.AtualizarItemCardapioUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.BuscarItemCardapioPorIdUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.CriarItemCardapioUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.ListarItensCardapioPorRestauranteUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.RemoverItemCardapioUsecase;

class ItemCardapioControllerTest {

	private final CriarItemCardapioUsecase criarItemCardapioUsecase = Mockito.mock(CriarItemCardapioUsecase.class);
	private final BuscarItemCardapioPorIdUsecase buscarItemCardapioPorIdUsecase = Mockito.mock(BuscarItemCardapioPorIdUsecase.class);
	private final ListarItensCardapioPorRestauranteUsecase listarItensCardapioPorRestauranteUsecase = Mockito.mock(
			ListarItensCardapioPorRestauranteUsecase.class);
	private final AtualizarItemCardapioUsecase atualizarItemCardapioUsecase = Mockito.mock(AtualizarItemCardapioUsecase.class);
	private final RemoverItemCardapioUsecase removerItemCardapioUsecase = Mockito.mock(RemoverItemCardapioUsecase.class);
	private final ItemCardapioMapper mapper = new ItemCardapioMapper();

	private final ItemCardapioController controller = new ItemCardapioController(
			criarItemCardapioUsecase,
			buscarItemCardapioPorIdUsecase,
			listarItensCardapioPorRestauranteUsecase,
			atualizarItemCardapioUsecase,
			removerItemCardapioUsecase,
			mapper);

	@Test
	void shouldCreateMenuItem() {
		var dto = new CriarItemCardapioInputDto("Risoto", "Descricao", new BigDecimal("49.90"), true, "/foto.jpg");
		var itemCardapio = new ItemCardapio(null, 10L, "Risoto", "Descricao", new BigDecimal("49.90"), true, "/foto.jpg");
		when(criarItemCardapioUsecase.criar(itemCardapio)).thenReturn(1L);

		var id = controller.criar(10L, dto);

		assertEquals(1L, id);
	}

	@Test
	void shouldReturnMenuItemById() {
		when(buscarItemCardapioPorIdUsecase.obterPorId(10L, 1L))
				.thenReturn(new ItemCardapio(1L, 10L, "Risoto", "Descricao", new BigDecimal("49.90"), true, "/foto.jpg"));

		var output = controller.obterPorId(10L, 1L);

		assertEquals(1L, output.id());
		assertEquals(10L, output.restauranteId());
	}

	@Test
	void shouldListMenuItemsByRestaurant() {
		when(listarItensCardapioPorRestauranteUsecase.listar(10L)).thenReturn(List.of(
				new ItemCardapio(1L, 10L, "Risoto", "Descricao 1", new BigDecimal("49.90"), true, "/foto-1.jpg"),
				new ItemCardapio(2L, 10L, "Massa", "Descricao 2", new BigDecimal("39.90"), false, "/foto-2.jpg")));

		var output = controller.listar(10L);

		assertEquals(2, output.size());
		assertEquals("Risoto", output.get(0).nome());
		verify(listarItensCardapioPorRestauranteUsecase).listar(10L);
	}

	@Test
	void shouldUpdateMenuItem() {
		var itemAtualizado = new ItemCardapio(1L, 10L, "Risoto Atualizado", "Descricao", new BigDecimal("54.90"), false, "/foto.jpg");
		when(atualizarItemCardapioUsecase.atualizar(itemAtualizado)).thenReturn(itemAtualizado);

		var output = controller.atualizar(10L, 1L, new AtualizarItemCardapioInputDto(
				"Risoto Atualizado",
				"Descricao",
				new BigDecimal("54.90"),
				false,
				"/foto.jpg"));

		assertEquals(1L, output.id());
		assertEquals("Risoto Atualizado", output.nome());
	}

	@Test
	void shouldRemoveMenuItem() {
		controller.remover(10L, 1L);

		verify(removerItemCardapioUsecase).remover(10L, 1L);
	}
}

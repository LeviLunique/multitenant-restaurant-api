package com.restauranthub.multitenant_restaurant_api.core.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.restauranthub.multitenant_restaurant_api.core.domain.ItemCardapio;
import com.restauranthub.multitenant_restaurant_api.core.dto.AtualizarItemCardapioInputDto;
import com.restauranthub.multitenant_restaurant_api.core.dto.CriarItemCardapioInputDto;

class ItemCardapioMapperTest {

	private final ItemCardapioMapper mapper = new ItemCardapioMapper();

	@Test
	void shouldMapCreateInputDtoToDomain() {
		var itemCardapio = mapper.map(10L, new CriarItemCardapioInputDto(
				"Risoto",
				"Risoto de cogumelos",
				new BigDecimal("49.90"),
				true,
				"/fotos/risoto.jpg"));

		assertEquals(10L, itemCardapio.getRestauranteId());
		assertEquals("Risoto", itemCardapio.getNome());
	}

	@Test
	void shouldMapUpdateInputDtoToDomain() {
		var itemCardapio = mapper.map(10L, 1L, new AtualizarItemCardapioInputDto(
				"Risoto Atualizado",
				"Descricao atualizada",
				new BigDecimal("54.90"),
				false,
				"/fotos/risoto-atualizado.jpg"));

		assertEquals(1L, itemCardapio.getId());
		assertEquals(10L, itemCardapio.getRestauranteId());
		assertEquals("Risoto Atualizado", itemCardapio.getNome());
	}

	@Test
	void shouldMapDomainToOutputDto() {
		var outputDto = mapper.map(new ItemCardapio(
				1L,
				10L,
				"Risoto",
				"Risoto de cogumelos",
				new BigDecimal("49.90"),
				true,
				"/fotos/risoto.jpg"));

		assertEquals(1L, outputDto.id());
		assertEquals(10L, outputDto.restauranteId());
		assertEquals("Risoto", outputDto.nome());
	}

	@Test
	void shouldMapDomainListToOutputDtoList() {
		var outputDtos = mapper.map(List.of(
				new ItemCardapio(1L, 10L, "Risoto", "Descricao 1", new BigDecimal("49.90"), true, "/fotos/1.jpg"),
				new ItemCardapio(2L, 10L, "Massa", "Descricao 2", new BigDecimal("39.90"), false, "/fotos/2.jpg")));

		assertEquals(2, outputDtos.size());
		assertEquals("Risoto", outputDtos.get(0).nome());
		assertEquals("Massa", outputDtos.get(1).nome());
	}
}

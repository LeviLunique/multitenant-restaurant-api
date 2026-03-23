package com.restauranthub.multitenant_restaurant_api.core.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.restauranthub.multitenant_restaurant_api.core.domain.Restaurante;
import com.restauranthub.multitenant_restaurant_api.core.dto.AtualizarRestauranteInputDto;
import com.restauranthub.multitenant_restaurant_api.core.dto.CriarRestauranteInputDto;

class RestauranteMapperTest {

	private final RestauranteMapper mapper = new RestauranteMapper();

	@Test
	void shouldMapCreateInputDtoToDomain() {
		var dto = new CriarRestauranteInputDto("Bistrô Central", "Rua A, 100", "Francesa", "09:00-22:00", 10L);

		var restaurante = mapper.map(dto);

		assertEquals("Bistrô Central", restaurante.getNome());
		assertEquals("Rua A, 100", restaurante.getEndereco());
		assertEquals("Francesa", restaurante.getTipoCozinha());
		assertEquals("09:00-22:00", restaurante.getHorarioFuncionamento());
		assertEquals(10L, restaurante.getDonoUsuarioId());
	}

	@Test
	void shouldMapUpdateInputDtoToDomain() {
		var dto = new AtualizarRestauranteInputDto("Bistrô Atualizado", "Rua B, 200", "Italiana", "10:00-23:00", 11L);

		var restaurante = mapper.map(1L, dto);

		assertEquals(1L, restaurante.getId());
		assertEquals("Bistrô Atualizado", restaurante.getNome());
		assertEquals("Rua B, 200", restaurante.getEndereco());
		assertEquals("Italiana", restaurante.getTipoCozinha());
		assertEquals("10:00-23:00", restaurante.getHorarioFuncionamento());
		assertEquals(11L, restaurante.getDonoUsuarioId());
	}

	@Test
	void shouldMapDomainToOutputDto() {
		var restaurante = new Restaurante(1L, "Bistrô Central", "Rua A, 100", "Francesa", "09:00-22:00", 10L);

		var dto = mapper.map(restaurante);

		assertEquals(1L, dto.id());
		assertEquals("Bistrô Central", dto.nome());
		assertEquals("Rua A, 100", dto.endereco());
		assertEquals("Francesa", dto.tipoCozinha());
		assertEquals("09:00-22:00", dto.horarioFuncionamento());
		assertEquals(10L, dto.donoUsuarioId());
	}

	@Test
	void shouldMapDomainListToOutputList() {
		var restaurantes = List.of(
				new Restaurante(1L, "Bistrô Central", "Rua A, 100", "Francesa", "09:00-22:00", 10L),
				new Restaurante(2L, "Cantina Sul", "Rua B, 200", "Italiana", "10:00-23:00", 11L));

		var dtos = mapper.map(restaurantes);

		assertEquals(2, dtos.size());
		assertEquals("Bistrô Central", dtos.get(0).nome());
		assertEquals("Cantina Sul", dtos.get(1).nome());
	}
}

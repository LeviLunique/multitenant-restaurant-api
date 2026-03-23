package com.restauranthub.multitenant_restaurant_api.core.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuario;
import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuarioEnum;
import com.restauranthub.multitenant_restaurant_api.core.dto.AtualizarTipoUsuarioInputDto;
import com.restauranthub.multitenant_restaurant_api.core.dto.CriarTipoUsuarioInputDto;

class TipoUsuarioMapperTest {

	private final TipoUsuarioMapper mapper = new TipoUsuarioMapper();

	@Test
	void shouldMapCreateInputDtoToDomain() {
		var dto = new CriarTipoUsuarioInputDto("Cliente", TipoUsuarioEnum.CLIENTE);

		var tipoUsuario = mapper.map(dto);

		assertEquals("Cliente", tipoUsuario.getNome());
		assertEquals(TipoUsuarioEnum.CLIENTE, tipoUsuario.getTipo());
	}

	@Test
	void shouldMapUpdateInputDtoToDomain() {
		var dto = new AtualizarTipoUsuarioInputDto("Dono de Restaurante", TipoUsuarioEnum.DONO);

		var tipoUsuario = mapper.map(1L, dto);

		assertEquals(1L, tipoUsuario.getId());
		assertEquals("Dono de Restaurante", tipoUsuario.getNome());
		assertEquals(TipoUsuarioEnum.DONO, tipoUsuario.getTipo());
	}

	@Test
	void shouldMapDomainToOutputDto() {
		var tipoUsuario = new TipoUsuario(1L, "Cliente", TipoUsuarioEnum.CLIENTE);

		var dto = mapper.map(tipoUsuario);

		assertEquals(1L, dto.id());
		assertEquals("Cliente", dto.nome());
		assertEquals(TipoUsuarioEnum.CLIENTE, dto.tipo());
	}

	@Test
	void shouldMapDomainListToOutputList() {
		var tiposUsuario = List.of(
				new TipoUsuario(1L, "Cliente", TipoUsuarioEnum.CLIENTE),
				new TipoUsuario(2L, "Dono de Restaurante", TipoUsuarioEnum.DONO));

		var dtos = mapper.map(tiposUsuario);

		assertEquals(2, dtos.size());
		assertEquals("Cliente", dtos.get(0).nome());
		assertEquals(TipoUsuarioEnum.CLIENTE, dtos.get(0).tipo());
		assertEquals("Dono de Restaurante", dtos.get(1).nome());
		assertEquals(TipoUsuarioEnum.DONO, dtos.get(1).tipo());
	}
}

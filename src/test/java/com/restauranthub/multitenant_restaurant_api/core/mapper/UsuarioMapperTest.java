package com.restauranthub.multitenant_restaurant_api.core.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuario;
import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuarioEnum;
import com.restauranthub.multitenant_restaurant_api.core.domain.Usuario;
import com.restauranthub.multitenant_restaurant_api.core.dto.CriarUsuarioInputDto;

class UsuarioMapperTest {

	private final UsuarioMapper mapper = new UsuarioMapper();

	@Test
	void shouldMapInputDtoToDomain() {
		var dto = new CriarUsuarioInputDto("Levi Lunique", "levi@example.com");

		var usuario = mapper.map(dto);

		assertEquals("Levi Lunique", usuario.getNome());
		assertEquals("levi@example.com", usuario.getEmail());
	}

	@Test
	void shouldMapDomainToOutputDto() {
		var usuario = new Usuario(1L, "Levi Lunique", "levi@example.com");
		usuario.associarTipoUsuario(new TipoUsuario(2L, "Cliente", TipoUsuarioEnum.CLIENTE));

		var dto = mapper.map(usuario);

		assertEquals(1L, dto.id());
		assertEquals("Levi Lunique", dto.nome());
		assertEquals("levi@example.com", dto.email());
		assertEquals(1, dto.tiposUsuario().size());
		assertEquals("Cliente", dto.tiposUsuario().get(0).nome());
		assertEquals(TipoUsuarioEnum.CLIENTE, dto.tiposUsuario().get(0).tipo());
	}

	@Test
	void shouldMapDomainListToOutputDtoList() {
		var usuarios = List.of(
				new Usuario(1L, "Levi Lunique", "levi@example.com"),
				new Usuario(2L, "Maria Silva", "maria@example.com"));

		var dtos = mapper.map(usuarios);

		assertEquals(2, dtos.size());
		assertEquals("Levi Lunique", dtos.get(0).nome());
		assertEquals("Maria Silva", dtos.get(1).nome());
	}
}

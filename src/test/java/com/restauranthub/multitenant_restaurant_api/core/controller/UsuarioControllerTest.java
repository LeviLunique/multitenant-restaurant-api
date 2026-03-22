package com.restauranthub.multitenant_restaurant_api.core.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.restauranthub.multitenant_restaurant_api.core.domain.Usuario;
import com.restauranthub.multitenant_restaurant_api.core.dto.CriarUsuarioInputDto;
import com.restauranthub.multitenant_restaurant_api.core.mapper.UsuarioMapper;
import com.restauranthub.multitenant_restaurant_api.core.usecase.BuscarUsuarioPorIdUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.CriarUsuarioUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.ListarUsuariosUsecase;

class UsuarioControllerTest {

	private final CriarUsuarioUsecase criarUsuarioUsecase = Mockito.mock(CriarUsuarioUsecase.class);
	private final BuscarUsuarioPorIdUsecase buscarUsuarioPorIdUsecase = Mockito.mock(BuscarUsuarioPorIdUsecase.class);
	private final ListarUsuariosUsecase listarUsuariosUsecase = Mockito.mock(ListarUsuariosUsecase.class);
	private final UsuarioMapper mapper = new UsuarioMapper();

	private final UsuarioController controller = new UsuarioController(
			criarUsuarioUsecase,
			buscarUsuarioPorIdUsecase,
			listarUsuariosUsecase,
			mapper);

	@Test
	void shouldCreateUser() {
		var dto = new CriarUsuarioInputDto("Levi Lunique", "levi@example.com");
		when(criarUsuarioUsecase.criar(new Usuario(null, "Levi Lunique", "levi@example.com"))).thenReturn(1L);

		var id = controller.criar(dto);

		assertEquals(1L, id);
	}

	@Test
	void shouldReturnUserById() {
		var usuario = new Usuario(1L, "Levi Lunique", "levi@example.com");
		when(buscarUsuarioPorIdUsecase.obterPorId(1L)).thenReturn(usuario);

		var output = controller.obterPorId(1L);

		assertEquals(1L, output.id());
		assertEquals("Levi Lunique", output.nome());
		assertEquals("levi@example.com", output.email());
	}

	@Test
	void shouldListUsers() {
		when(listarUsuariosUsecase.listar()).thenReturn(List.of(
				new Usuario(1L, "Levi Lunique", "levi@example.com"),
				new Usuario(2L, "Maria Silva", "maria@example.com")));

		var output = controller.listar();

		assertEquals(2, output.size());
		assertEquals("Levi Lunique", output.get(0).nome());
		assertEquals("Maria Silva", output.get(1).nome());
		verify(listarUsuariosUsecase).listar();
	}
}

package com.restauranthub.multitenant_restaurant_api.core.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.restauranthub.multitenant_restaurant_api.core.domain.Usuario;
import com.restauranthub.multitenant_restaurant_api.core.dto.AtualizarUsuarioInputDto;
import com.restauranthub.multitenant_restaurant_api.core.dto.CriarUsuarioInputDto;
import com.restauranthub.multitenant_restaurant_api.core.mapper.UsuarioMapper;
import com.restauranthub.multitenant_restaurant_api.core.usecase.AtualizarUsuarioUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.BuscarUsuarioPorIdUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.CriarUsuarioUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.ListarUsuariosUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.RemoverUsuarioUsecase;

class UsuarioControllerTest {

	private final CriarUsuarioUsecase criarUsuarioUsecase = Mockito.mock(CriarUsuarioUsecase.class);
	private final BuscarUsuarioPorIdUsecase buscarUsuarioPorIdUsecase = Mockito.mock(BuscarUsuarioPorIdUsecase.class);
	private final ListarUsuariosUsecase listarUsuariosUsecase = Mockito.mock(ListarUsuariosUsecase.class);
	private final AtualizarUsuarioUsecase atualizarUsuarioUsecase = Mockito.mock(AtualizarUsuarioUsecase.class);
	private final RemoverUsuarioUsecase removerUsuarioUsecase = Mockito.mock(RemoverUsuarioUsecase.class);
	private final UsuarioMapper mapper = new UsuarioMapper();

	private final UsuarioController controller = new UsuarioController(
			criarUsuarioUsecase,
			buscarUsuarioPorIdUsecase,
			listarUsuariosUsecase,
			atualizarUsuarioUsecase,
			removerUsuarioUsecase,
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
		assertEquals(0, output.tiposUsuario().size());
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

	@Test
	void shouldUpdateUser() {
		when(atualizarUsuarioUsecase.atualizar(new Usuario(1L, "Levi Atualizado", "levi.atualizado@example.com")))
				.thenReturn(new Usuario(1L, "Levi Atualizado", "levi.atualizado@example.com"));

		var output = controller.atualizar(1L, new AtualizarUsuarioInputDto("Levi Atualizado", "levi.atualizado@example.com"));

		assertEquals(1L, output.id());
		assertEquals("Levi Atualizado", output.nome());
		assertEquals("levi.atualizado@example.com", output.email());
	}

	@Test
	void shouldRemoveUser() {
		controller.remover(1L);

		verify(removerUsuarioUsecase).remover(1L);
	}
}

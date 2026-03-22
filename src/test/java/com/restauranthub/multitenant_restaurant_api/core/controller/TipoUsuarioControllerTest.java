package com.restauranthub.multitenant_restaurant_api.core.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuario;
import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuarioEnum;
import com.restauranthub.multitenant_restaurant_api.core.domain.Usuario;
import com.restauranthub.multitenant_restaurant_api.core.dto.AtualizarTipoUsuarioInputDto;
import com.restauranthub.multitenant_restaurant_api.core.dto.CriarTipoUsuarioInputDto;
import com.restauranthub.multitenant_restaurant_api.core.mapper.TipoUsuarioMapper;
import com.restauranthub.multitenant_restaurant_api.core.mapper.UsuarioMapper;
import com.restauranthub.multitenant_restaurant_api.core.usecase.AssociarTipoUsuarioAoUsuarioUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.AtualizarTipoUsuarioUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.BuscarTipoUsuarioPorIdUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.CriarTipoUsuarioUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.ListarTiposUsuarioUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.RemoverTipoUsuarioUsecase;

class TipoUsuarioControllerTest {

	private final CriarTipoUsuarioUsecase criarTipoUsuarioUsecase = Mockito.mock(CriarTipoUsuarioUsecase.class);
	private final BuscarTipoUsuarioPorIdUsecase buscarTipoUsuarioPorIdUsecase = Mockito.mock(BuscarTipoUsuarioPorIdUsecase.class);
	private final ListarTiposUsuarioUsecase listarTiposUsuarioUsecase = Mockito.mock(ListarTiposUsuarioUsecase.class);
	private final AtualizarTipoUsuarioUsecase atualizarTipoUsuarioUsecase = Mockito.mock(AtualizarTipoUsuarioUsecase.class);
	private final RemoverTipoUsuarioUsecase removerTipoUsuarioUsecase = Mockito.mock(RemoverTipoUsuarioUsecase.class);
	private final AssociarTipoUsuarioAoUsuarioUsecase associarTipoUsuarioAoUsuarioUsecase = Mockito
			.mock(AssociarTipoUsuarioAoUsuarioUsecase.class);
	private final TipoUsuarioMapper tipoUsuarioMapper = new TipoUsuarioMapper();
	private final UsuarioMapper usuarioMapper = new UsuarioMapper();

	private final TipoUsuarioController controller = new TipoUsuarioController(
			criarTipoUsuarioUsecase,
			buscarTipoUsuarioPorIdUsecase,
			listarTiposUsuarioUsecase,
			atualizarTipoUsuarioUsecase,
			removerTipoUsuarioUsecase,
			associarTipoUsuarioAoUsuarioUsecase,
			tipoUsuarioMapper,
			usuarioMapper);

	@Test
	void shouldCreateUserType() {
		var dto = new CriarTipoUsuarioInputDto("Cliente", TipoUsuarioEnum.CLIENTE);
		when(criarTipoUsuarioUsecase.criar(new TipoUsuario(null, "Cliente", TipoUsuarioEnum.CLIENTE))).thenReturn(1L);

		var id = controller.criar(dto);

		assertEquals(1L, id);
	}

	@Test
	void shouldReturnUserTypeById() {
		when(buscarTipoUsuarioPorIdUsecase.obterPorId(1L))
				.thenReturn(new TipoUsuario(1L, "Cliente", TipoUsuarioEnum.CLIENTE));

		var output = controller.obterPorId(1L);

		assertEquals(1L, output.id());
		assertEquals("Cliente", output.nome());
		assertEquals(TipoUsuarioEnum.CLIENTE, output.tipo());
	}

	@Test
	void shouldListUserTypes() {
		when(listarTiposUsuarioUsecase.listar()).thenReturn(List.of(
				new TipoUsuario(1L, "Cliente", TipoUsuarioEnum.CLIENTE),
				new TipoUsuario(2L, "Dono de Restaurante", TipoUsuarioEnum.DONO)));

		var output = controller.listar();

		assertEquals(2, output.size());
		assertEquals("Cliente", output.get(0).nome());
		assertEquals(TipoUsuarioEnum.CLIENTE, output.get(0).tipo());
		assertEquals("Dono de Restaurante", output.get(1).nome());
		assertEquals(TipoUsuarioEnum.DONO, output.get(1).tipo());
		verify(listarTiposUsuarioUsecase).listar();
	}

	@Test
	void shouldUpdateUserType() {
		when(atualizarTipoUsuarioUsecase.atualizar(new TipoUsuario(1L, "Dono de Restaurante", TipoUsuarioEnum.DONO)))
				.thenReturn(new TipoUsuario(1L, "Dono de Restaurante", TipoUsuarioEnum.DONO));

		var output = controller.atualizar(1L, new AtualizarTipoUsuarioInputDto("Dono de Restaurante", TipoUsuarioEnum.DONO));

		assertEquals(1L, output.id());
		assertEquals("Dono de Restaurante", output.nome());
		assertEquals(TipoUsuarioEnum.DONO, output.tipo());
	}

	@Test
	void shouldRemoveUserType() {
		controller.remover(1L);

		verify(removerTipoUsuarioUsecase).remover(1L);
	}

	@Test
	void shouldAssociateUserTypeToUser() {
		var usuario = new Usuario(1L, "Levi Lunique", "levi@example.com");
		usuario.associarTipoUsuario(new TipoUsuario(2L, "Cliente", TipoUsuarioEnum.CLIENTE));
		when(associarTipoUsuarioAoUsuarioUsecase.associar(1L, 2L)).thenReturn(usuario);

		var output = controller.associarAoUsuario(1L, 2L);

		assertEquals(1L, output.id());
		assertEquals(1, output.tiposUsuario().size());
		assertEquals("Cliente", output.tiposUsuario().get(0).nome());
		assertEquals(TipoUsuarioEnum.CLIENTE, output.tiposUsuario().get(0).tipo());
	}
}

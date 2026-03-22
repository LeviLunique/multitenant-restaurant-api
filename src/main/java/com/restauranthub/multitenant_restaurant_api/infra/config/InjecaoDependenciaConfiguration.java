package com.restauranthub.multitenant_restaurant_api.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.restauranthub.multitenant_restaurant_api.core.controller.TipoUsuarioController;
import com.restauranthub.multitenant_restaurant_api.core.controller.UsuarioController;
import com.restauranthub.multitenant_restaurant_api.core.gateway.TipoUsuarioGateway;
import com.restauranthub.multitenant_restaurant_api.core.gateway.UsuarioGateway;
import com.restauranthub.multitenant_restaurant_api.core.mapper.TipoUsuarioMapper;
import com.restauranthub.multitenant_restaurant_api.core.mapper.UsuarioMapper;
import com.restauranthub.multitenant_restaurant_api.core.usecase.AssociarTipoUsuarioAoUsuarioUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.AssociarTipoUsuarioAoUsuarioUsecaseImpl;
import com.restauranthub.multitenant_restaurant_api.core.usecase.AtualizarTipoUsuarioUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.AtualizarTipoUsuarioUsecaseImpl;
import com.restauranthub.multitenant_restaurant_api.core.usecase.AtualizarUsuarioUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.AtualizarUsuarioUsecaseImpl;
import com.restauranthub.multitenant_restaurant_api.core.usecase.BuscarTipoUsuarioPorIdUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.BuscarTipoUsuarioPorIdUsecaseImpl;
import com.restauranthub.multitenant_restaurant_api.core.usecase.BuscarUsuarioPorIdUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.BuscarUsuarioPorIdUsecaseImpl;
import com.restauranthub.multitenant_restaurant_api.core.usecase.CriarTipoUsuarioUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.CriarTipoUsuarioUsecaseImpl;
import com.restauranthub.multitenant_restaurant_api.core.usecase.CriarUsuarioUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.CriarUsuarioUsecaseImpl;
import com.restauranthub.multitenant_restaurant_api.core.usecase.ListarTiposUsuarioUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.ListarTiposUsuarioUsecaseImpl;
import com.restauranthub.multitenant_restaurant_api.core.usecase.ListarUsuariosUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.ListarUsuariosUsecaseImpl;
import com.restauranthub.multitenant_restaurant_api.core.usecase.RemoverTipoUsuarioUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.RemoverTipoUsuarioUsecaseImpl;
import com.restauranthub.multitenant_restaurant_api.core.usecase.RemoverUsuarioUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.RemoverUsuarioUsecaseImpl;

@Configuration
public class InjecaoDependenciaConfiguration {

	@Bean
	public UsuarioMapper usuarioMapper() {
		return new UsuarioMapper();
	}

	@Bean
	public TipoUsuarioMapper tipoUsuarioMapper() {
		return new TipoUsuarioMapper();
	}

	@Bean
	public CriarUsuarioUsecase criarUsuarioUsecase(UsuarioGateway usuarioGateway) {
		return new CriarUsuarioUsecaseImpl(usuarioGateway);
	}

	@Bean
	public BuscarUsuarioPorIdUsecase buscarUsuarioPorIdUsecase(UsuarioGateway usuarioGateway) {
		return new BuscarUsuarioPorIdUsecaseImpl(usuarioGateway);
	}

	@Bean
	public ListarUsuariosUsecase listarUsuariosUsecase(UsuarioGateway usuarioGateway) {
		return new ListarUsuariosUsecaseImpl(usuarioGateway);
	}

	@Bean
	public AtualizarUsuarioUsecase atualizarUsuarioUsecase(UsuarioGateway usuarioGateway) {
		return new AtualizarUsuarioUsecaseImpl(usuarioGateway);
	}

	@Bean
	public RemoverUsuarioUsecase removerUsuarioUsecase(UsuarioGateway usuarioGateway) {
		return new RemoverUsuarioUsecaseImpl(usuarioGateway);
	}

	@Bean
	public CriarTipoUsuarioUsecase criarTipoUsuarioUsecase(TipoUsuarioGateway tipoUsuarioGateway) {
		return new CriarTipoUsuarioUsecaseImpl(tipoUsuarioGateway);
	}

	@Bean
	public BuscarTipoUsuarioPorIdUsecase buscarTipoUsuarioPorIdUsecase(TipoUsuarioGateway tipoUsuarioGateway) {
		return new BuscarTipoUsuarioPorIdUsecaseImpl(tipoUsuarioGateway);
	}

	@Bean
	public ListarTiposUsuarioUsecase listarTiposUsuarioUsecase(TipoUsuarioGateway tipoUsuarioGateway) {
		return new ListarTiposUsuarioUsecaseImpl(tipoUsuarioGateway);
	}

	@Bean
	public AtualizarTipoUsuarioUsecase atualizarTipoUsuarioUsecase(TipoUsuarioGateway tipoUsuarioGateway) {
		return new AtualizarTipoUsuarioUsecaseImpl(tipoUsuarioGateway);
	}

	@Bean
	public RemoverTipoUsuarioUsecase removerTipoUsuarioUsecase(TipoUsuarioGateway tipoUsuarioGateway) {
		return new RemoverTipoUsuarioUsecaseImpl(tipoUsuarioGateway);
	}

	@Bean
	public AssociarTipoUsuarioAoUsuarioUsecase associarTipoUsuarioAoUsuarioUsecase(
			UsuarioGateway usuarioGateway,
			TipoUsuarioGateway tipoUsuarioGateway) {
		return new AssociarTipoUsuarioAoUsuarioUsecaseImpl(usuarioGateway, tipoUsuarioGateway);
	}

	@Bean
	public UsuarioController usuarioController(
			CriarUsuarioUsecase criarUsuarioUsecase,
			BuscarUsuarioPorIdUsecase buscarUsuarioPorIdUsecase,
			ListarUsuariosUsecase listarUsuariosUsecase,
			AtualizarUsuarioUsecase atualizarUsuarioUsecase,
			RemoverUsuarioUsecase removerUsuarioUsecase,
			UsuarioMapper usuarioMapper) {
		return new UsuarioController(
				criarUsuarioUsecase,
				buscarUsuarioPorIdUsecase,
				listarUsuariosUsecase,
				atualizarUsuarioUsecase,
				removerUsuarioUsecase,
				usuarioMapper);
	}

	@Bean
	public TipoUsuarioController tipoUsuarioController(
			CriarTipoUsuarioUsecase criarTipoUsuarioUsecase,
			BuscarTipoUsuarioPorIdUsecase buscarTipoUsuarioPorIdUsecase,
			ListarTiposUsuarioUsecase listarTiposUsuarioUsecase,
			AtualizarTipoUsuarioUsecase atualizarTipoUsuarioUsecase,
			RemoverTipoUsuarioUsecase removerTipoUsuarioUsecase,
			AssociarTipoUsuarioAoUsuarioUsecase associarTipoUsuarioAoUsuarioUsecase,
			TipoUsuarioMapper tipoUsuarioMapper,
			UsuarioMapper usuarioMapper) {
		return new TipoUsuarioController(
				criarTipoUsuarioUsecase,
				buscarTipoUsuarioPorIdUsecase,
				listarTiposUsuarioUsecase,
				atualizarTipoUsuarioUsecase,
				removerTipoUsuarioUsecase,
				associarTipoUsuarioAoUsuarioUsecase,
				tipoUsuarioMapper,
				usuarioMapper);
	}
}

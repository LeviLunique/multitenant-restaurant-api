package com.restauranthub.multitenant_restaurant_api.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.restauranthub.multitenant_restaurant_api.core.controller.UsuarioController;
import com.restauranthub.multitenant_restaurant_api.core.gateway.UsuarioGateway;
import com.restauranthub.multitenant_restaurant_api.core.mapper.UsuarioMapper;
import com.restauranthub.multitenant_restaurant_api.core.usecase.BuscarUsuarioPorIdUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.BuscarUsuarioPorIdUsecaseImpl;
import com.restauranthub.multitenant_restaurant_api.core.usecase.CriarUsuarioUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.CriarUsuarioUsecaseImpl;
import com.restauranthub.multitenant_restaurant_api.core.usecase.ListarUsuariosUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.ListarUsuariosUsecaseImpl;

@Configuration
public class InjecaoDependenciaConfiguration {

	@Bean
	public UsuarioMapper usuarioMapper() {
		return new UsuarioMapper();
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
	public UsuarioController usuarioController(
			CriarUsuarioUsecase criarUsuarioUsecase,
			BuscarUsuarioPorIdUsecase buscarUsuarioPorIdUsecase,
			ListarUsuariosUsecase listarUsuariosUsecase,
			UsuarioMapper usuarioMapper) {
		return new UsuarioController(
				criarUsuarioUsecase,
				buscarUsuarioPorIdUsecase,
				listarUsuariosUsecase,
				usuarioMapper);
	}
}

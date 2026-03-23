package com.restauranthub.multitenant_restaurant_api.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.restauranthub.multitenant_restaurant_api.core.controller.TipoUsuarioController;
import com.restauranthub.multitenant_restaurant_api.core.controller.ItemCardapioController;
import com.restauranthub.multitenant_restaurant_api.core.controller.RestauranteController;
import com.restauranthub.multitenant_restaurant_api.core.controller.UsuarioController;
import com.restauranthub.multitenant_restaurant_api.core.gateway.ItemCardapioGateway;
import com.restauranthub.multitenant_restaurant_api.core.gateway.RestauranteGateway;
import com.restauranthub.multitenant_restaurant_api.core.gateway.TipoUsuarioGateway;
import com.restauranthub.multitenant_restaurant_api.core.gateway.UsuarioGateway;
import com.restauranthub.multitenant_restaurant_api.core.mapper.ItemCardapioMapper;
import com.restauranthub.multitenant_restaurant_api.core.mapper.RestauranteMapper;
import com.restauranthub.multitenant_restaurant_api.core.mapper.TipoUsuarioMapper;
import com.restauranthub.multitenant_restaurant_api.core.mapper.UsuarioMapper;
import com.restauranthub.multitenant_restaurant_api.core.usecase.AtualizarItemCardapioUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.AtualizarItemCardapioUsecaseImpl;
import com.restauranthub.multitenant_restaurant_api.core.usecase.AtualizarRestauranteUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.AtualizarRestauranteUsecaseImpl;
import com.restauranthub.multitenant_restaurant_api.core.usecase.AssociarTipoUsuarioAoUsuarioUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.AssociarTipoUsuarioAoUsuarioUsecaseImpl;
import com.restauranthub.multitenant_restaurant_api.core.usecase.AtualizarTipoUsuarioUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.AtualizarTipoUsuarioUsecaseImpl;
import com.restauranthub.multitenant_restaurant_api.core.usecase.AtualizarUsuarioUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.AtualizarUsuarioUsecaseImpl;
import com.restauranthub.multitenant_restaurant_api.core.usecase.BuscarItemCardapioPorIdUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.BuscarItemCardapioPorIdUsecaseImpl;
import com.restauranthub.multitenant_restaurant_api.core.usecase.BuscarRestaurantePorIdUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.BuscarRestaurantePorIdUsecaseImpl;
import com.restauranthub.multitenant_restaurant_api.core.usecase.BuscarTipoUsuarioPorIdUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.BuscarTipoUsuarioPorIdUsecaseImpl;
import com.restauranthub.multitenant_restaurant_api.core.usecase.BuscarUsuarioPorIdUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.BuscarUsuarioPorIdUsecaseImpl;
import com.restauranthub.multitenant_restaurant_api.core.usecase.CriarItemCardapioUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.CriarItemCardapioUsecaseImpl;
import com.restauranthub.multitenant_restaurant_api.core.usecase.CriarRestauranteUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.CriarRestauranteUsecaseImpl;
import com.restauranthub.multitenant_restaurant_api.core.usecase.CriarTipoUsuarioUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.CriarTipoUsuarioUsecaseImpl;
import com.restauranthub.multitenant_restaurant_api.core.usecase.CriarUsuarioUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.CriarUsuarioUsecaseImpl;
import com.restauranthub.multitenant_restaurant_api.core.usecase.ListarItensCardapioPorRestauranteUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.ListarItensCardapioPorRestauranteUsecaseImpl;
import com.restauranthub.multitenant_restaurant_api.core.usecase.ListarRestaurantesUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.ListarRestaurantesUsecaseImpl;
import com.restauranthub.multitenant_restaurant_api.core.usecase.ListarTiposUsuarioUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.ListarTiposUsuarioUsecaseImpl;
import com.restauranthub.multitenant_restaurant_api.core.usecase.ListarUsuariosUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.ListarUsuariosUsecaseImpl;
import com.restauranthub.multitenant_restaurant_api.core.usecase.RemoverItemCardapioUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.RemoverItemCardapioUsecaseImpl;
import com.restauranthub.multitenant_restaurant_api.core.usecase.RemoverRestauranteUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.RemoverRestauranteUsecaseImpl;
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
	public RestauranteMapper restauranteMapper() {
		return new RestauranteMapper();
	}

	@Bean
	public ItemCardapioMapper itemCardapioMapper() {
		return new ItemCardapioMapper();
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
	public CriarItemCardapioUsecase criarItemCardapioUsecase(ItemCardapioGateway itemCardapioGateway, RestauranteGateway restauranteGateway) {
		return new CriarItemCardapioUsecaseImpl(itemCardapioGateway, restauranteGateway);
	}

	@Bean
	public BuscarItemCardapioPorIdUsecase buscarItemCardapioPorIdUsecase(
			ItemCardapioGateway itemCardapioGateway,
			RestauranteGateway restauranteGateway) {
		return new BuscarItemCardapioPorIdUsecaseImpl(itemCardapioGateway, restauranteGateway);
	}

	@Bean
	public ListarItensCardapioPorRestauranteUsecase listarItensCardapioPorRestauranteUsecase(
			ItemCardapioGateway itemCardapioGateway,
			RestauranteGateway restauranteGateway) {
		return new ListarItensCardapioPorRestauranteUsecaseImpl(itemCardapioGateway, restauranteGateway);
	}

	@Bean
	public AtualizarItemCardapioUsecase atualizarItemCardapioUsecase(
			ItemCardapioGateway itemCardapioGateway,
			RestauranteGateway restauranteGateway) {
		return new AtualizarItemCardapioUsecaseImpl(itemCardapioGateway, restauranteGateway);
	}

	@Bean
	public RemoverItemCardapioUsecase removerItemCardapioUsecase(
			ItemCardapioGateway itemCardapioGateway,
			RestauranteGateway restauranteGateway) {
		return new RemoverItemCardapioUsecaseImpl(itemCardapioGateway, restauranteGateway);
	}

	@Bean
	public CriarRestauranteUsecase criarRestauranteUsecase(RestauranteGateway restauranteGateway, UsuarioGateway usuarioGateway) {
		return new CriarRestauranteUsecaseImpl(restauranteGateway, usuarioGateway);
	}

	@Bean
	public BuscarRestaurantePorIdUsecase buscarRestaurantePorIdUsecase(RestauranteGateway restauranteGateway) {
		return new BuscarRestaurantePorIdUsecaseImpl(restauranteGateway);
	}

	@Bean
	public ListarRestaurantesUsecase listarRestaurantesUsecase(RestauranteGateway restauranteGateway) {
		return new ListarRestaurantesUsecaseImpl(restauranteGateway);
	}

	@Bean
	public AtualizarRestauranteUsecase atualizarRestauranteUsecase(RestauranteGateway restauranteGateway, UsuarioGateway usuarioGateway) {
		return new AtualizarRestauranteUsecaseImpl(restauranteGateway, usuarioGateway);
	}

	@Bean
	public RemoverRestauranteUsecase removerRestauranteUsecase(RestauranteGateway restauranteGateway) {
		return new RemoverRestauranteUsecaseImpl(restauranteGateway);
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

	@Bean
	public RestauranteController restauranteController(
			CriarRestauranteUsecase criarRestauranteUsecase,
			BuscarRestaurantePorIdUsecase buscarRestaurantePorIdUsecase,
			ListarRestaurantesUsecase listarRestaurantesUsecase,
			AtualizarRestauranteUsecase atualizarRestauranteUsecase,
			RemoverRestauranteUsecase removerRestauranteUsecase,
			RestauranteMapper restauranteMapper) {
		return new RestauranteController(
				criarRestauranteUsecase,
				buscarRestaurantePorIdUsecase,
				listarRestaurantesUsecase,
				atualizarRestauranteUsecase,
				removerRestauranteUsecase,
				restauranteMapper);
	}

	@Bean
	public ItemCardapioController itemCardapioController(
			CriarItemCardapioUsecase criarItemCardapioUsecase,
			BuscarItemCardapioPorIdUsecase buscarItemCardapioPorIdUsecase,
			ListarItensCardapioPorRestauranteUsecase listarItensCardapioPorRestauranteUsecase,
			AtualizarItemCardapioUsecase atualizarItemCardapioUsecase,
			RemoverItemCardapioUsecase removerItemCardapioUsecase,
			ItemCardapioMapper itemCardapioMapper) {
		return new ItemCardapioController(
				criarItemCardapioUsecase,
				buscarItemCardapioPorIdUsecase,
				listarItensCardapioPorRestauranteUsecase,
				atualizarItemCardapioUsecase,
				removerItemCardapioUsecase,
				itemCardapioMapper);
	}
}

package com.restauranthub.multitenant_restaurant_api.core.gateway;

import java.util.List;
import java.util.Optional;

import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuario;
import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuarioEnum;

public interface TipoUsuarioGateway {

	Long criar(TipoUsuario tipoUsuario);

	Optional<TipoUsuario> obterPorId(Long id);

	Optional<TipoUsuario> obterPorNome(String nome);

	Optional<TipoUsuario> obterPorTipo(TipoUsuarioEnum tipo);

	List<TipoUsuario> listar();

	TipoUsuario atualizar(TipoUsuario tipoUsuario);

	void remover(Long id);
}

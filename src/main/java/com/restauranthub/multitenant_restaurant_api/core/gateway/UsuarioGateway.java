package com.restauranthub.multitenant_restaurant_api.core.gateway;

import java.util.List;
import java.util.Optional;

import com.restauranthub.multitenant_restaurant_api.core.domain.Usuario;

public interface UsuarioGateway {

	Long criar(Usuario usuario);

	Optional<Usuario> obterPorId(Long id);

	Optional<Usuario> obterPorEmail(String email);

	List<Usuario> listar();

	Usuario atualizar(Usuario usuario);

	void remover(Long id);
}

package com.restauranthub.multitenant_restaurant_api.infra.database.jpa;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.restauranthub.multitenant_restaurant_api.core.domain.Usuario;
import com.restauranthub.multitenant_restaurant_api.core.exception.InfrastructureException;
import com.restauranthub.multitenant_restaurant_api.core.gateway.UsuarioGateway;
import com.restauranthub.multitenant_restaurant_api.infra.database.mapper.UsuarioEntityMapper;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UsuarioJpaGateway implements UsuarioGateway {

	private static final String USER_REPOSITORY_ERROR_CODE = "USER_REPOSITORY_ERROR";
	private static final String COULD_NOT_PERSIST_USER_MESSAGE = "Could not persist user.";
	private static final String COULD_NOT_QUERY_USER_BY_ID_MESSAGE = "Could not query user by id.";
	private static final String COULD_NOT_QUERY_USER_BY_EMAIL_MESSAGE = "Could not query user by email.";
	private static final String COULD_NOT_LIST_USERS_MESSAGE = "Could not list users.";
	private static final String COULD_NOT_UPDATE_USER_MESSAGE = "Could not update user.";
	private static final String COULD_NOT_REMOVE_USER_MESSAGE = "Could not remove user.";

	private final UsuarioRepository usuarioRepository;
	private final UsuarioEntityMapper usuarioEntityMapper;

	@Override
	public Long criar(Usuario usuario) {
		try {
			return usuarioRepository.save(usuarioEntityMapper.map(usuario)).getId();
		} catch (Exception exception) {
			throw new InfrastructureException(USER_REPOSITORY_ERROR_CODE, COULD_NOT_PERSIST_USER_MESSAGE);
		}
	}

	@Override
	public Optional<Usuario> obterPorId(Long id) {
		try {
			return usuarioRepository.findById(id).map(usuarioEntityMapper::map);
		} catch (Exception exception) {
			throw new InfrastructureException(USER_REPOSITORY_ERROR_CODE, COULD_NOT_QUERY_USER_BY_ID_MESSAGE);
		}
	}

	@Override
	public Optional<Usuario> obterPorEmail(String email) {
		try {
			return usuarioRepository.findByEmail(email.toLowerCase(Locale.ROOT)).map(usuarioEntityMapper::map);
		} catch (Exception exception) {
			throw new InfrastructureException(USER_REPOSITORY_ERROR_CODE, COULD_NOT_QUERY_USER_BY_EMAIL_MESSAGE);
		}
	}

	@Override
	public List<Usuario> listar() {
		try {
			return usuarioRepository.findAllByOrderByIdAsc().stream().map(usuarioEntityMapper::map).toList();
		} catch (Exception exception) {
			throw new InfrastructureException(USER_REPOSITORY_ERROR_CODE, COULD_NOT_LIST_USERS_MESSAGE);
		}
	}

	@Override
	public Usuario atualizar(Usuario usuario) {
		try {
			return usuarioEntityMapper.map(usuarioRepository.save(usuarioEntityMapper.map(usuario)));
		} catch (Exception exception) {
			throw new InfrastructureException(USER_REPOSITORY_ERROR_CODE, COULD_NOT_UPDATE_USER_MESSAGE);
		}
	}

	@Override
	public void remover(Long id) {
		try {
			usuarioRepository.deleteById(id);
		} catch (Exception exception) {
			throw new InfrastructureException(USER_REPOSITORY_ERROR_CODE, COULD_NOT_REMOVE_USER_MESSAGE);
		}
	}
}

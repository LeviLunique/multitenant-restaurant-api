package com.restauranthub.multitenant_restaurant_api.infra.database.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuario;
import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuarioEnum;
import com.restauranthub.multitenant_restaurant_api.core.exception.InfrastructureException;
import com.restauranthub.multitenant_restaurant_api.core.gateway.TipoUsuarioGateway;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.repository.TipoUsuarioRepository;
import com.restauranthub.multitenant_restaurant_api.infra.database.mapper.TipoUsuarioEntityMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TipoUsuarioJpaGateway implements TipoUsuarioGateway {

	private static final String USER_TYPE_REPOSITORY_ERROR_CODE = "USER_TYPE_REPOSITORY_ERROR";
	private static final String COULD_NOT_PERSIST_USER_TYPE_MESSAGE = "Could not persist user type.";
	private static final String COULD_NOT_QUERY_USER_TYPE_BY_ID_MESSAGE = "Could not query user type by id.";
	private static final String COULD_NOT_QUERY_USER_TYPE_BY_NAME_MESSAGE = "Could not query user type by name.";
	private static final String COULD_NOT_QUERY_USER_TYPE_BY_ENUM_MESSAGE = "Could not query user type by enum.";
	private static final String COULD_NOT_LIST_USER_TYPES_MESSAGE = "Could not list user types.";
	private static final String COULD_NOT_UPDATE_USER_TYPE_MESSAGE = "Could not update user type.";
	private static final String COULD_NOT_REMOVE_USER_TYPE_MESSAGE = "Could not remove user type.";

	private final TipoUsuarioRepository tipoUsuarioRepository;
	private final TipoUsuarioEntityMapper tipoUsuarioEntityMapper;

	@Override
	public Long criar(TipoUsuario tipoUsuario) {
		try {
			return tipoUsuarioRepository.save(tipoUsuarioEntityMapper.map(tipoUsuario)).getId();
		} catch (Exception exception) {
			throw new InfrastructureException(USER_TYPE_REPOSITORY_ERROR_CODE, COULD_NOT_PERSIST_USER_TYPE_MESSAGE);
		}
	}

	@Override
	public Optional<TipoUsuario> obterPorId(Long id) {
		try {
			return tipoUsuarioRepository.findById(id).map(tipoUsuarioEntityMapper::map);
		} catch (Exception exception) {
			throw new InfrastructureException(USER_TYPE_REPOSITORY_ERROR_CODE, COULD_NOT_QUERY_USER_TYPE_BY_ID_MESSAGE);
		}
	}

	@Override
	public Optional<TipoUsuario> obterPorNome(String nome) {
		try {
			return tipoUsuarioRepository.findByNome(nome).map(tipoUsuarioEntityMapper::map);
		} catch (Exception exception) {
			throw new InfrastructureException(USER_TYPE_REPOSITORY_ERROR_CODE, COULD_NOT_QUERY_USER_TYPE_BY_NAME_MESSAGE);
		}
	}

	@Override
	public Optional<TipoUsuario> obterPorTipo(TipoUsuarioEnum tipo) {
		try {
			return tipoUsuarioRepository.findByTipo(tipo).map(tipoUsuarioEntityMapper::map);
		} catch (Exception exception) {
			throw new InfrastructureException(USER_TYPE_REPOSITORY_ERROR_CODE, COULD_NOT_QUERY_USER_TYPE_BY_ENUM_MESSAGE);
		}
	}

	@Override
	public List<TipoUsuario> listar() {
		try {
			return tipoUsuarioRepository.findAllByOrderByIdAsc().stream().map(tipoUsuarioEntityMapper::map).toList();
		} catch (Exception exception) {
			throw new InfrastructureException(USER_TYPE_REPOSITORY_ERROR_CODE, COULD_NOT_LIST_USER_TYPES_MESSAGE);
		}
	}

	@Override
	public TipoUsuario atualizar(TipoUsuario tipoUsuario) {
		try {
			return tipoUsuarioEntityMapper.map(tipoUsuarioRepository.save(tipoUsuarioEntityMapper.map(tipoUsuario)));
		} catch (Exception exception) {
			throw new InfrastructureException(USER_TYPE_REPOSITORY_ERROR_CODE, COULD_NOT_UPDATE_USER_TYPE_MESSAGE);
		}
	}

	@Override
	public void remover(Long id) {
		try {
			tipoUsuarioRepository.deleteById(id);
		} catch (Exception exception) {
			throw new InfrastructureException(USER_TYPE_REPOSITORY_ERROR_CODE, COULD_NOT_REMOVE_USER_TYPE_MESSAGE);
		}
	}
}

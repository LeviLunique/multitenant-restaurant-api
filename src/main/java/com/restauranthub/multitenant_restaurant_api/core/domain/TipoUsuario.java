package com.restauranthub.multitenant_restaurant_api.core.domain;

import com.restauranthub.multitenant_restaurant_api.core.exception.BusinessException;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class TipoUsuario {

	private static final String INVALID_USER_TYPE_NAME_CODE = "INVALID_USER_TYPE_NAME";
	private static final String INVALID_USER_TYPE_NAME_MESSAGE = "User type name must be provided.";
	private static final String INVALID_USER_TYPE_ENUM_CODE = "INVALID_USER_TYPE_ENUM";
	private static final String INVALID_USER_TYPE_ENUM_MESSAGE = "User type enum must be provided.";

	private final Long id;
	private final String nome;
	private final TipoUsuarioEnum tipo;

	public TipoUsuario(Long id, String nome, TipoUsuarioEnum tipo) {
		validarNome(nome);
		validarTipo(tipo);
		this.id = id;
		this.nome = nome.trim();
		this.tipo = tipo;
	}

	private void validarNome(String nome) {
		if (nome == null || nome.isBlank()) {
			throw new BusinessException(INVALID_USER_TYPE_NAME_CODE, INVALID_USER_TYPE_NAME_MESSAGE);
		}
	}

	private void validarTipo(TipoUsuarioEnum tipo) {
		if (tipo == null) {
			throw new BusinessException(INVALID_USER_TYPE_ENUM_CODE, INVALID_USER_TYPE_ENUM_MESSAGE);
		}
	}
}

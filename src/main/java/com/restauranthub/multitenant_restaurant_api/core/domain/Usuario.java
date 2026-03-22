package com.restauranthub.multitenant_restaurant_api.core.domain;

import java.util.Locale;
import java.util.regex.Pattern;

import com.restauranthub.multitenant_restaurant_api.core.exception.BusinessException;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Usuario {

	private static final String INVALID_USER_NAME_CODE = "INVALID_USER_NAME";
	private static final String INVALID_USER_NAME_MESSAGE = "User name must be provided.";
	private static final String INVALID_USER_EMAIL_CODE = "INVALID_USER_EMAIL";
	private static final String INVALID_USER_EMAIL_MESSAGE = "User email must be valid.";
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

	private final Long id;
	private final String nome;
	private final String email;

	public Usuario(Long id, String nome, String email) {
		validarNome(nome);
		validarEmail(email);
		this.id = id;
		this.nome = nome.trim();
		this.email = email.trim().toLowerCase(Locale.ROOT);
	}

	private void validarNome(String nome) {
		if (nome == null || nome.isBlank()) {
			throw new BusinessException(INVALID_USER_NAME_CODE, INVALID_USER_NAME_MESSAGE);
		}
	}

	private void validarEmail(String email) {
		if (email == null || email.isBlank() || !EMAIL_PATTERN.matcher(email.trim()).matches()) {
			throw new BusinessException(INVALID_USER_EMAIL_CODE, INVALID_USER_EMAIL_MESSAGE);
		}
	}
}

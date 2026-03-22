package com.restauranthub.multitenant_restaurant_api.core.domain;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
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
	private static final String USER_ALREADY_ASSOCIATED_WITH_TYPE_CODE = "USER_ALREADY_ASSOCIATED_WITH_TYPE";
	private static final String USER_ALREADY_ASSOCIATED_WITH_TYPE_MESSAGE = "User is already associated with the provided type.";
	private static final String INVALID_USER_TYPE_CODE = "INVALID_USER_TYPE";
	private static final String INVALID_USER_TYPE_MESSAGE = "User type must be provided.";
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

	private final Long id;
	private final String nome;
	private final String email;
	private final Set<TipoUsuario> tiposUsuario;

	public Usuario(Long id, String nome, String email) {
		this(id, nome, email, Set.of());
	}

	public Usuario(Long id, String nome, String email, Set<TipoUsuario> tiposUsuario) {
		validarNome(nome);
		validarEmail(email);
		this.id = id;
		this.nome = nome.trim();
		this.email = email.trim().toLowerCase(Locale.ROOT);
		this.tiposUsuario = new LinkedHashSet<>(tiposUsuario == null ? Set.of() : tiposUsuario);
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

	public void associarTipoUsuario(TipoUsuario tipoUsuario) {
		if (tipoUsuario == null) {
			throw new BusinessException(INVALID_USER_TYPE_CODE, INVALID_USER_TYPE_MESSAGE);
		}
		if (tiposUsuario.contains(tipoUsuario)) {
			throw new BusinessException(USER_ALREADY_ASSOCIATED_WITH_TYPE_CODE, USER_ALREADY_ASSOCIATED_WITH_TYPE_MESSAGE);
		}
		tiposUsuario.add(tipoUsuario);
	}

	public Set<TipoUsuario> getTiposUsuario() {
		return java.util.Collections.unmodifiableSet(new LinkedHashSet<>(tiposUsuario));
	}
}

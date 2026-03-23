package com.restauranthub.multitenant_restaurant_api.core.domain;

import com.restauranthub.multitenant_restaurant_api.core.exception.BusinessException;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Restaurante {

	private static final String INVALID_RESTAURANT_NAME_CODE = "INVALID_RESTAURANT_NAME";
	private static final String INVALID_RESTAURANT_NAME_MESSAGE = "Restaurant name must be provided.";
	private static final String INVALID_RESTAURANT_ADDRESS_CODE = "INVALID_RESTAURANT_ADDRESS";
	private static final String INVALID_RESTAURANT_ADDRESS_MESSAGE = "Restaurant address must be provided.";
	private static final String INVALID_RESTAURANT_CUISINE_TYPE_CODE = "INVALID_RESTAURANT_CUISINE_TYPE";
	private static final String INVALID_RESTAURANT_CUISINE_TYPE_MESSAGE = "Restaurant cuisine type must be provided.";
	private static final String INVALID_RESTAURANT_OPENING_HOURS_CODE = "INVALID_RESTAURANT_OPENING_HOURS";
	private static final String INVALID_RESTAURANT_OPENING_HOURS_MESSAGE = "Restaurant opening hours must be provided.";
	private static final String INVALID_RESTAURANT_OWNER_CODE = "INVALID_RESTAURANT_OWNER";
	private static final String INVALID_RESTAURANT_OWNER_MESSAGE = "Restaurant owner must be provided.";

	private final Long id;
	private final String nome;
	private final String endereco;
	private final String tipoCozinha;
	private final String horarioFuncionamento;
	private final Long donoUsuarioId;

	public Restaurante(
			Long id,
			String nome,
			String endereco,
			String tipoCozinha,
			String horarioFuncionamento,
			Long donoUsuarioId) {
		validarNome(nome);
		validarEndereco(endereco);
		validarTipoCozinha(tipoCozinha);
		validarHorarioFuncionamento(horarioFuncionamento);
		validarDonoUsuarioId(donoUsuarioId);
		this.id = id;
		this.nome = nome.trim();
		this.endereco = endereco.trim();
		this.tipoCozinha = tipoCozinha.trim();
		this.horarioFuncionamento = horarioFuncionamento.trim();
		this.donoUsuarioId = donoUsuarioId;
	}

	private void validarNome(String nome) {
		if (nome == null || nome.isBlank()) {
			throw new BusinessException(INVALID_RESTAURANT_NAME_CODE, INVALID_RESTAURANT_NAME_MESSAGE);
		}
	}

	private void validarEndereco(String endereco) {
		if (endereco == null || endereco.isBlank()) {
			throw new BusinessException(INVALID_RESTAURANT_ADDRESS_CODE, INVALID_RESTAURANT_ADDRESS_MESSAGE);
		}
	}

	private void validarTipoCozinha(String tipoCozinha) {
		if (tipoCozinha == null || tipoCozinha.isBlank()) {
			throw new BusinessException(INVALID_RESTAURANT_CUISINE_TYPE_CODE, INVALID_RESTAURANT_CUISINE_TYPE_MESSAGE);
		}
	}

	private void validarHorarioFuncionamento(String horarioFuncionamento) {
		if (horarioFuncionamento == null || horarioFuncionamento.isBlank()) {
			throw new BusinessException(INVALID_RESTAURANT_OPENING_HOURS_CODE, INVALID_RESTAURANT_OPENING_HOURS_MESSAGE);
		}
	}

	private void validarDonoUsuarioId(Long donoUsuarioId) {
		if (donoUsuarioId == null || donoUsuarioId <= 0L) {
			throw new BusinessException(INVALID_RESTAURANT_OWNER_CODE, INVALID_RESTAURANT_OWNER_MESSAGE);
		}
	}
}

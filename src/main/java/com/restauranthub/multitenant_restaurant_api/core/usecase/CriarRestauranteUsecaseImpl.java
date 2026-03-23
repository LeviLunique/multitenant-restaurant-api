package com.restauranthub.multitenant_restaurant_api.core.usecase;

import com.restauranthub.multitenant_restaurant_api.core.domain.Restaurante;
import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuarioEnum;
import com.restauranthub.multitenant_restaurant_api.core.exception.BusinessException;
import com.restauranthub.multitenant_restaurant_api.core.exception.ResourceNotFoundException;
import com.restauranthub.multitenant_restaurant_api.core.gateway.RestauranteGateway;
import com.restauranthub.multitenant_restaurant_api.core.gateway.UsuarioGateway;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CriarRestauranteUsecaseImpl implements CriarRestauranteUsecase {

	private static final String RESTAURANT_OWNER_NOT_FOUND_CODE = "RESTAURANT_OWNER_NOT_FOUND";
	private static final String RESTAURANT_OWNER_NOT_FOUND_MESSAGE = "Restaurant owner not found.";
	private static final String INVALID_RESTAURANT_OWNER_TYPE_CODE = "INVALID_RESTAURANT_OWNER_TYPE";
	private static final String INVALID_RESTAURANT_OWNER_TYPE_MESSAGE = "Restaurant owner must have owner type.";

	private final RestauranteGateway restauranteGateway;
	private final UsuarioGateway usuarioGateway;

	@Override
	public Long criar(Restaurante restaurante) {
		var dono = usuarioGateway.obterPorId(restaurante.getDonoUsuarioId())
				.orElseThrow(() -> new ResourceNotFoundException(RESTAURANT_OWNER_NOT_FOUND_CODE, RESTAURANT_OWNER_NOT_FOUND_MESSAGE));

		var possuiTipoDono = dono.getTiposUsuario().stream().anyMatch(tipoUsuario -> tipoUsuario.getTipo() == TipoUsuarioEnum.DONO);
		if (!possuiTipoDono) {
			throw new BusinessException(INVALID_RESTAURANT_OWNER_TYPE_CODE, INVALID_RESTAURANT_OWNER_TYPE_MESSAGE);
		}

		return restauranteGateway.criar(restaurante);
	}
}

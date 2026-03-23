package com.restauranthub.multitenant_restaurant_api.infra.database.mapper;

import org.springframework.stereotype.Component;

import com.restauranthub.multitenant_restaurant_api.core.domain.Restaurante;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.entity.RestauranteEntity;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.entity.UsuarioEntity;

@Component
public class RestauranteEntityMapper {

	public Restaurante map(RestauranteEntity entity) {
		return new Restaurante(
				entity.getId(),
				entity.getNome(),
				entity.getEndereco(),
				entity.getTipoCozinha(),
				entity.getHorarioFuncionamento(),
				entity.getDono().getId());
	}

	public RestauranteEntity map(Restaurante restaurante) {
		return new RestauranteEntity(
				restaurante.getId(),
				restaurante.getNome(),
				restaurante.getEndereco(),
				restaurante.getTipoCozinha(),
				restaurante.getHorarioFuncionamento(),
				new UsuarioEntity(restaurante.getDonoUsuarioId(), null, null));
	}
}

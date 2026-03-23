package com.restauranthub.multitenant_restaurant_api.core.mapper;

import java.util.List;

import com.restauranthub.multitenant_restaurant_api.core.domain.Restaurante;
import com.restauranthub.multitenant_restaurant_api.core.dto.AtualizarRestauranteInputDto;
import com.restauranthub.multitenant_restaurant_api.core.dto.CriarRestauranteInputDto;
import com.restauranthub.multitenant_restaurant_api.core.dto.RestauranteOutputDto;

public class RestauranteMapper {

	public Restaurante map(CriarRestauranteInputDto dto) {
		return new Restaurante(null, dto.nome(), dto.endereco(), dto.tipoCozinha(), dto.horarioFuncionamento(), dto.donoUsuarioId());
	}

	public Restaurante map(Long id, AtualizarRestauranteInputDto dto) {
		return new Restaurante(id, dto.nome(), dto.endereco(), dto.tipoCozinha(), dto.horarioFuncionamento(), dto.donoUsuarioId());
	}

	public RestauranteOutputDto map(Restaurante restaurante) {
		return new RestauranteOutputDto(
				restaurante.getId(),
				restaurante.getNome(),
				restaurante.getEndereco(),
				restaurante.getTipoCozinha(),
				restaurante.getHorarioFuncionamento(),
				restaurante.getDonoUsuarioId());
	}

	public List<RestauranteOutputDto> map(List<Restaurante> restaurantes) {
		return restaurantes.stream().map(this::map).toList();
	}
}

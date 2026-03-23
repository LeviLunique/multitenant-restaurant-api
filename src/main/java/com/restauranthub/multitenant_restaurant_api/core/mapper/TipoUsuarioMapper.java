package com.restauranthub.multitenant_restaurant_api.core.mapper;

import java.util.List;

import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuario;
import com.restauranthub.multitenant_restaurant_api.core.dto.AtualizarTipoUsuarioInputDto;
import com.restauranthub.multitenant_restaurant_api.core.dto.CriarTipoUsuarioInputDto;
import com.restauranthub.multitenant_restaurant_api.core.dto.TipoUsuarioOutputDto;

public class TipoUsuarioMapper {

	public TipoUsuario map(CriarTipoUsuarioInputDto dto) {
		return new TipoUsuario(null, dto.nome(), dto.tipo());
	}

	public TipoUsuario map(Long id, AtualizarTipoUsuarioInputDto dto) {
		return new TipoUsuario(id, dto.nome(), dto.tipo());
	}

	public TipoUsuarioOutputDto map(TipoUsuario tipoUsuario) {
		return new TipoUsuarioOutputDto(tipoUsuario.getId(), tipoUsuario.getNome(), tipoUsuario.getTipo());
	}

	public List<TipoUsuarioOutputDto> map(List<TipoUsuario> tiposUsuario) {
		return tiposUsuario.stream().map(this::map).toList();
	}
}

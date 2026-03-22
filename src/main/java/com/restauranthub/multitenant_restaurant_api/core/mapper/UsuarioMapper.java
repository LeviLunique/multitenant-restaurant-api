package com.restauranthub.multitenant_restaurant_api.core.mapper;

import java.util.List;

import com.restauranthub.multitenant_restaurant_api.core.domain.Usuario;
import com.restauranthub.multitenant_restaurant_api.core.dto.CriarUsuarioInputDto;
import com.restauranthub.multitenant_restaurant_api.core.dto.UsuarioOutputDto;

public class UsuarioMapper {

	public Usuario map(CriarUsuarioInputDto dto) {
		return new Usuario(null, dto.nome(), dto.email());
	}

	public UsuarioOutputDto map(Usuario usuario) {
		return new UsuarioOutputDto(usuario.getId(), usuario.getNome(), usuario.getEmail());
	}

	public List<UsuarioOutputDto> map(List<Usuario> usuarios) {
		return usuarios.stream().map(this::map).toList();
	}
}

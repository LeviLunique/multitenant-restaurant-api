package com.restauranthub.multitenant_restaurant_api.core.mapper;

import java.util.List;

import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuario;
import com.restauranthub.multitenant_restaurant_api.core.domain.Usuario;
import com.restauranthub.multitenant_restaurant_api.core.dto.AtualizarUsuarioInputDto;
import com.restauranthub.multitenant_restaurant_api.core.dto.CriarUsuarioInputDto;
import com.restauranthub.multitenant_restaurant_api.core.dto.TipoUsuarioOutputDto;
import com.restauranthub.multitenant_restaurant_api.core.dto.UsuarioOutputDto;

public class UsuarioMapper {

	public Usuario map(CriarUsuarioInputDto dto) {
		return new Usuario(null, dto.nome(), dto.email());
	}

	public Usuario map(Long id, AtualizarUsuarioInputDto dto) {
		return new Usuario(id, dto.nome(), dto.email());
	}

	public UsuarioOutputDto map(Usuario usuario) {
		return new UsuarioOutputDto(
				usuario.getId(),
				usuario.getNome(),
				usuario.getEmail(),
				usuario.getTiposUsuario().stream().map(this::mapTipoUsuario).toList());
	}

	public List<UsuarioOutputDto> map(List<Usuario> usuarios) {
		return usuarios.stream().map(this::map).toList();
	}

	private TipoUsuarioOutputDto mapTipoUsuario(TipoUsuario tipoUsuario) {
		return new TipoUsuarioOutputDto(tipoUsuario.getId(), tipoUsuario.getNome(), tipoUsuario.getTipo());
	}
}

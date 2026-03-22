package com.restauranthub.multitenant_restaurant_api.core.dto;

import java.util.List;

public record UsuarioOutputDto(
		Long id,
		String nome,
		String email,
		List<TipoUsuarioOutputDto> tiposUsuario) {
}

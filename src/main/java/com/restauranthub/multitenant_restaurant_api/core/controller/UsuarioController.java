package com.restauranthub.multitenant_restaurant_api.core.controller;

import java.util.List;

import com.restauranthub.multitenant_restaurant_api.core.dto.AtualizarUsuarioInputDto;
import com.restauranthub.multitenant_restaurant_api.core.dto.CriarUsuarioInputDto;
import com.restauranthub.multitenant_restaurant_api.core.dto.UsuarioOutputDto;
import com.restauranthub.multitenant_restaurant_api.core.mapper.UsuarioMapper;
import com.restauranthub.multitenant_restaurant_api.core.usecase.AtualizarUsuarioUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.BuscarUsuarioPorIdUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.CriarUsuarioUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.ListarUsuariosUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.RemoverUsuarioUsecase;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UsuarioController {

	private final CriarUsuarioUsecase criarUsuarioUsecase;
	private final BuscarUsuarioPorIdUsecase buscarUsuarioPorIdUsecase;
	private final ListarUsuariosUsecase listarUsuariosUsecase;
	private final AtualizarUsuarioUsecase atualizarUsuarioUsecase;
	private final RemoverUsuarioUsecase removerUsuarioUsecase;
	private final UsuarioMapper usuarioMapper;

	public Long criar(CriarUsuarioInputDto criarUsuarioInputDto) {
		var usuario = usuarioMapper.map(criarUsuarioInputDto);
		return criarUsuarioUsecase.criar(usuario);
	}

	public UsuarioOutputDto obterPorId(Long id) {
		return usuarioMapper.map(buscarUsuarioPorIdUsecase.obterPorId(id));
	}

	public List<UsuarioOutputDto> listar() {
		return usuarioMapper.map(listarUsuariosUsecase.listar());
	}

	public UsuarioOutputDto atualizar(Long id, AtualizarUsuarioInputDto atualizarUsuarioInputDto) {
		var usuario = usuarioMapper.map(id, atualizarUsuarioInputDto);
		return usuarioMapper.map(atualizarUsuarioUsecase.atualizar(usuario));
	}

	public void remover(Long id) {
		removerUsuarioUsecase.remover(id);
	}
}

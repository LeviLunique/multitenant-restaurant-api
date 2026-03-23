package com.restauranthub.multitenant_restaurant_api.core.controller;

import java.util.List;

import com.restauranthub.multitenant_restaurant_api.core.dto.AtualizarTipoUsuarioInputDto;
import com.restauranthub.multitenant_restaurant_api.core.dto.CriarTipoUsuarioInputDto;
import com.restauranthub.multitenant_restaurant_api.core.dto.TipoUsuarioOutputDto;
import com.restauranthub.multitenant_restaurant_api.core.dto.UsuarioOutputDto;
import com.restauranthub.multitenant_restaurant_api.core.mapper.TipoUsuarioMapper;
import com.restauranthub.multitenant_restaurant_api.core.mapper.UsuarioMapper;
import com.restauranthub.multitenant_restaurant_api.core.usecase.AssociarTipoUsuarioAoUsuarioUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.AtualizarTipoUsuarioUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.BuscarTipoUsuarioPorIdUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.CriarTipoUsuarioUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.ListarTiposUsuarioUsecase;
import com.restauranthub.multitenant_restaurant_api.core.usecase.RemoverTipoUsuarioUsecase;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TipoUsuarioController {

	private final CriarTipoUsuarioUsecase criarTipoUsuarioUsecase;
	private final BuscarTipoUsuarioPorIdUsecase buscarTipoUsuarioPorIdUsecase;
	private final ListarTiposUsuarioUsecase listarTiposUsuarioUsecase;
	private final AtualizarTipoUsuarioUsecase atualizarTipoUsuarioUsecase;
	private final RemoverTipoUsuarioUsecase removerTipoUsuarioUsecase;
	private final AssociarTipoUsuarioAoUsuarioUsecase associarTipoUsuarioAoUsuarioUsecase;
	private final TipoUsuarioMapper tipoUsuarioMapper;
	private final UsuarioMapper usuarioMapper;

	public Long criar(CriarTipoUsuarioInputDto criarTipoUsuarioInputDto) {
		var tipoUsuario = tipoUsuarioMapper.map(criarTipoUsuarioInputDto);
		return criarTipoUsuarioUsecase.criar(tipoUsuario);
	}

	public TipoUsuarioOutputDto obterPorId(Long id) {
		return tipoUsuarioMapper.map(buscarTipoUsuarioPorIdUsecase.obterPorId(id));
	}

	public List<TipoUsuarioOutputDto> listar() {
		return tipoUsuarioMapper.map(listarTiposUsuarioUsecase.listar());
	}

	public TipoUsuarioOutputDto atualizar(Long id, AtualizarTipoUsuarioInputDto atualizarTipoUsuarioInputDto) {
		var tipoUsuario = tipoUsuarioMapper.map(id, atualizarTipoUsuarioInputDto);
		return tipoUsuarioMapper.map(atualizarTipoUsuarioUsecase.atualizar(tipoUsuario));
	}

	public void remover(Long id) {
		removerTipoUsuarioUsecase.remover(id);
	}

	public UsuarioOutputDto associarAoUsuario(Long usuarioId, Long tipoUsuarioId) {
		return usuarioMapper.map(associarTipoUsuarioAoUsuarioUsecase.associar(usuarioId, tipoUsuarioId));
	}
}

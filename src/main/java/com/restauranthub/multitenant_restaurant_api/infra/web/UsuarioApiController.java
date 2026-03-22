package com.restauranthub.multitenant_restaurant_api.infra.web;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restauranthub.multitenant_restaurant_api.core.controller.UsuarioController;
import com.restauranthub.multitenant_restaurant_api.core.dto.CriarUsuarioInputDto;
import com.restauranthub.multitenant_restaurant_api.core.dto.UsuarioOutputDto;
import com.restauranthub.multitenant_restaurant_api.infra.web.json.UsuarioJson;
import com.restauranthub.multitenant_restaurant_api.infra.web.json.UsuarioResponseJson;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioApiController {

	private static final String USUARIOS_PATH = "/usuarios/";

	private final UsuarioController usuarioController;

	@PostMapping
	public ResponseEntity<UsuarioResponseJson> criar(@Valid @RequestBody UsuarioJson usuarioJson) {
		var criarUsuarioInputDto = new CriarUsuarioInputDto(usuarioJson.nome(), usuarioJson.email());
		var id = usuarioController.criar(criarUsuarioInputDto);
		var usuarioCriado = usuarioController.obterPorId(id);
		return ResponseEntity.created(URI.create(USUARIOS_PATH + id)).body(map(usuarioCriado));
	}

	@GetMapping("/{id}")
	public UsuarioResponseJson obterPorId(@PathVariable Long id) {
		return map(usuarioController.obterPorId(id));
	}

	@GetMapping
	public List<UsuarioResponseJson> listar() {
		return usuarioController.listar().stream().map(this::map).toList();
	}

	private UsuarioResponseJson map(UsuarioOutputDto usuarioOutputDto) {
		return new UsuarioResponseJson(usuarioOutputDto.id(), usuarioOutputDto.nome(), usuarioOutputDto.email());
	}
}

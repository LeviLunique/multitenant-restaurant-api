package com.restauranthub.multitenant_restaurant_api.infra.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restauranthub.multitenant_restaurant_api.core.controller.UsuarioController;
import com.restauranthub.multitenant_restaurant_api.core.dto.AtualizarUsuarioInputDto;
import com.restauranthub.multitenant_restaurant_api.core.dto.CriarUsuarioInputDto;
import com.restauranthub.multitenant_restaurant_api.core.dto.UsuarioOutputDto;
import com.restauranthub.multitenant_restaurant_api.infra.web.json.TipoUsuarioResponseJson;
import com.restauranthub.multitenant_restaurant_api.infra.web.json.UsuarioJson;
import com.restauranthub.multitenant_restaurant_api.infra.web.json.UsuarioResponseJson;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioApiController {

	private final UsuarioController usuarioController;

	@PostMapping
	public ResponseEntity<UsuarioResponseJson> criar(@Valid @RequestBody UsuarioJson usuarioJson) {
		var criarUsuarioInputDto = new CriarUsuarioInputDto(usuarioJson.nome(), usuarioJson.email());
		var id = usuarioController.criar(criarUsuarioInputDto);
		var usuarioCriado = usuarioController.obterPorId(id);
		var location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(id)
				.toUri();
		return ResponseEntity.created(location).body(map(usuarioCriado));
	}

	@GetMapping("/{id}")
	public UsuarioResponseJson obterPorId(@PathVariable Long id) {
		return map(usuarioController.obterPorId(id));
	}

	@GetMapping
	public List<UsuarioResponseJson> listar() {
		return usuarioController.listar().stream().map(this::map).toList();
	}

	@PutMapping("/{id}")
	public UsuarioResponseJson atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioJson usuarioJson) {
		return map(usuarioController.atualizar(id, new AtualizarUsuarioInputDto(usuarioJson.nome(), usuarioJson.email())));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		usuarioController.remover(id);
		return ResponseEntity.noContent().build();
	}

	private UsuarioResponseJson map(UsuarioOutputDto usuarioOutputDto) {
		return new UsuarioResponseJson(
				usuarioOutputDto.id(),
				usuarioOutputDto.nome(),
				usuarioOutputDto.email(),
				usuarioOutputDto.tiposUsuario().stream()
						.map(tipoUsuario -> new TipoUsuarioResponseJson(tipoUsuario.id(), tipoUsuario.nome(), tipoUsuario.tipo()))
						.toList());
	}
}

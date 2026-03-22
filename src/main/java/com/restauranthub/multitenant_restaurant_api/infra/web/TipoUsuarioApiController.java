package com.restauranthub.multitenant_restaurant_api.infra.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.restauranthub.multitenant_restaurant_api.core.controller.TipoUsuarioController;
import com.restauranthub.multitenant_restaurant_api.core.dto.AtualizarTipoUsuarioInputDto;
import com.restauranthub.multitenant_restaurant_api.core.dto.CriarTipoUsuarioInputDto;
import com.restauranthub.multitenant_restaurant_api.core.dto.TipoUsuarioOutputDto;
import com.restauranthub.multitenant_restaurant_api.infra.web.json.TipoUsuarioJson;
import com.restauranthub.multitenant_restaurant_api.infra.web.json.TipoUsuarioResponseJson;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tipos-usuario")
@RequiredArgsConstructor
public class TipoUsuarioApiController {

	private final TipoUsuarioController tipoUsuarioController;

	@PostMapping
	public ResponseEntity<TipoUsuarioResponseJson> criar(@Valid @RequestBody TipoUsuarioJson tipoUsuarioJson) {
		var id = tipoUsuarioController.criar(new CriarTipoUsuarioInputDto(tipoUsuarioJson.nome(), tipoUsuarioJson.tipo()));
		var tipoUsuarioCriado = tipoUsuarioController.obterPorId(id);
		var location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(id)
				.toUri();
		return ResponseEntity.created(location).body(map(tipoUsuarioCriado));
	}

	@GetMapping("/{id}")
	public TipoUsuarioResponseJson obterPorId(@PathVariable Long id) {
		return map(tipoUsuarioController.obterPorId(id));
	}

	@GetMapping
	public List<TipoUsuarioResponseJson> listar() {
		return tipoUsuarioController.listar().stream().map(this::map).toList();
	}

	@PutMapping("/{id}")
	public TipoUsuarioResponseJson atualizar(@PathVariable Long id, @Valid @RequestBody TipoUsuarioJson tipoUsuarioJson) {
		return map(tipoUsuarioController.atualizar(id, new AtualizarTipoUsuarioInputDto(tipoUsuarioJson.nome(), tipoUsuarioJson.tipo())));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		tipoUsuarioController.remover(id);
		return ResponseEntity.noContent().build();
	}

	private TipoUsuarioResponseJson map(TipoUsuarioOutputDto tipoUsuarioOutputDto) {
		return new TipoUsuarioResponseJson(tipoUsuarioOutputDto.id(), tipoUsuarioOutputDto.nome(), tipoUsuarioOutputDto.tipo());
	}
}

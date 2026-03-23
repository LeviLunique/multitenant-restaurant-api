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

import com.restauranthub.multitenant_restaurant_api.core.controller.RestauranteController;
import com.restauranthub.multitenant_restaurant_api.core.dto.AtualizarRestauranteInputDto;
import com.restauranthub.multitenant_restaurant_api.core.dto.CriarRestauranteInputDto;
import com.restauranthub.multitenant_restaurant_api.core.dto.RestauranteOutputDto;
import com.restauranthub.multitenant_restaurant_api.infra.web.json.RestauranteJson;
import com.restauranthub.multitenant_restaurant_api.infra.web.json.RestauranteResponseJson;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/restaurantes")
@RequiredArgsConstructor
public class RestauranteApiController {

	private final RestauranteController restauranteController;

	@PostMapping
	public ResponseEntity<RestauranteResponseJson> criar(@Valid @RequestBody RestauranteJson restauranteJson) {
		var id = restauranteController.criar(new CriarRestauranteInputDto(
				restauranteJson.nome(),
				restauranteJson.endereco(),
				restauranteJson.tipoCozinha(),
				restauranteJson.horarioFuncionamento(),
				restauranteJson.donoUsuarioId()));
		var restauranteCriado = restauranteController.obterPorId(id);
		var location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(id)
				.toUri();
		return ResponseEntity.created(location).body(map(restauranteCriado));
	}

	@GetMapping("/{id}")
	public RestauranteResponseJson obterPorId(@PathVariable Long id) {
		return map(restauranteController.obterPorId(id));
	}

	@GetMapping
	public List<RestauranteResponseJson> listar() {
		return restauranteController.listar().stream().map(this::map).toList();
	}

	@PutMapping("/{id}")
	public RestauranteResponseJson atualizar(@PathVariable Long id, @Valid @RequestBody RestauranteJson restauranteJson) {
		return map(restauranteController.atualizar(id, new AtualizarRestauranteInputDto(
				restauranteJson.nome(),
				restauranteJson.endereco(),
				restauranteJson.tipoCozinha(),
				restauranteJson.horarioFuncionamento(),
				restauranteJson.donoUsuarioId())));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		restauranteController.remover(id);
		return ResponseEntity.noContent().build();
	}

	private RestauranteResponseJson map(RestauranteOutputDto restauranteOutputDto) {
		return new RestauranteResponseJson(
				restauranteOutputDto.id(),
				restauranteOutputDto.nome(),
				restauranteOutputDto.endereco(),
				restauranteOutputDto.tipoCozinha(),
				restauranteOutputDto.horarioFuncionamento(),
				restauranteOutputDto.donoUsuarioId());
	}
}

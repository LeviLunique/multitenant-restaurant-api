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

import com.restauranthub.multitenant_restaurant_api.core.controller.ItemCardapioController;
import com.restauranthub.multitenant_restaurant_api.core.dto.AtualizarItemCardapioInputDto;
import com.restauranthub.multitenant_restaurant_api.core.dto.CriarItemCardapioInputDto;
import com.restauranthub.multitenant_restaurant_api.core.dto.ItemCardapioOutputDto;
import com.restauranthub.multitenant_restaurant_api.infra.web.json.ItemCardapioJson;
import com.restauranthub.multitenant_restaurant_api.infra.web.json.ItemCardapioResponseJson;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/itens-cardapio")
@RequiredArgsConstructor
public class ItemCardapioApiController {

	private final ItemCardapioController itemCardapioController;

	@PostMapping
	public ResponseEntity<ItemCardapioResponseJson> criar(
			@PathVariable Long restauranteId,
			@Valid @RequestBody ItemCardapioJson itemCardapioJson) {
		var id = itemCardapioController.criar(restauranteId, new CriarItemCardapioInputDto(
				itemCardapioJson.nome(),
				itemCardapioJson.descricao(),
				itemCardapioJson.preco(),
				itemCardapioJson.apenasConsumoNoLocal(),
				itemCardapioJson.caminhoFoto()));
		var itemCriado = itemCardapioController.obterPorId(restauranteId, id);
		var location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
		return ResponseEntity.created(location).body(map(itemCriado));
	}

	@GetMapping("/{id}")
	public ItemCardapioResponseJson obterPorId(@PathVariable Long restauranteId, @PathVariable Long id) {
		return map(itemCardapioController.obterPorId(restauranteId, id));
	}

	@GetMapping
	public List<ItemCardapioResponseJson> listar(@PathVariable Long restauranteId) {
		return itemCardapioController.listar(restauranteId).stream().map(this::map).toList();
	}

	@PutMapping("/{id}")
	public ItemCardapioResponseJson atualizar(
			@PathVariable Long restauranteId,
			@PathVariable Long id,
			@Valid @RequestBody ItemCardapioJson itemCardapioJson) {
		return map(itemCardapioController.atualizar(restauranteId, id, new AtualizarItemCardapioInputDto(
				itemCardapioJson.nome(),
				itemCardapioJson.descricao(),
				itemCardapioJson.preco(),
				itemCardapioJson.apenasConsumoNoLocal(),
				itemCardapioJson.caminhoFoto())));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long restauranteId, @PathVariable Long id) {
		itemCardapioController.remover(restauranteId, id);
		return ResponseEntity.noContent().build();
	}

	private ItemCardapioResponseJson map(ItemCardapioOutputDto itemCardapioOutputDto) {
		return new ItemCardapioResponseJson(
				itemCardapioOutputDto.id(),
				itemCardapioOutputDto.restauranteId(),
				itemCardapioOutputDto.nome(),
				itemCardapioOutputDto.descricao(),
				itemCardapioOutputDto.preco(),
				itemCardapioOutputDto.apenasConsumoNoLocal(),
				itemCardapioOutputDto.caminhoFoto());
	}
}

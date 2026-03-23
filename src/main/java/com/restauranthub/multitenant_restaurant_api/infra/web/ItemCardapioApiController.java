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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/itens-cardapio")
@RequiredArgsConstructor
@Tag(name = "Itens de Cardapio", description = "Operations for restaurant menu items.")
public class ItemCardapioApiController {

	private final ItemCardapioController itemCardapioController;

	@PostMapping
	@Operation(summary = "Create menu item", description = "Creates a menu item under a restaurant.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Menu item created successfully."),
			@ApiResponse(responseCode = "400", description = "Invalid request payload.", ref = "#/components/responses/BadRequestResponse"),
			@ApiResponse(responseCode = "404", description = "Restaurant not found.", ref = "#/components/responses/NotFoundResponse"),
			@ApiResponse(responseCode = "422", description = "Business rule violation.", ref = "#/components/responses/UnprocessableEntityResponse") })
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
	@Operation(summary = "Get menu item by id", description = "Returns a menu item scoped by restaurant.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Menu item found."),
			@ApiResponse(responseCode = "404", description = "Restaurant or menu item not found.", ref = "#/components/responses/NotFoundResponse") })
	public ItemCardapioResponseJson obterPorId(@PathVariable Long restauranteId, @PathVariable Long id) {
		return map(itemCardapioController.obterPorId(restauranteId, id));
	}

	@GetMapping
	@Operation(summary = "List menu items", description = "Returns all menu items for a restaurant.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Menu items returned successfully."),
			@ApiResponse(responseCode = "404", description = "Restaurant not found.", ref = "#/components/responses/NotFoundResponse") })
	public List<ItemCardapioResponseJson> listar(@PathVariable Long restauranteId) {
		return itemCardapioController.listar(restauranteId).stream().map(this::map).toList();
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update menu item", description = "Updates a menu item scoped by restaurant.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Menu item updated successfully."),
			@ApiResponse(responseCode = "400", description = "Invalid request payload.", ref = "#/components/responses/BadRequestResponse"),
			@ApiResponse(responseCode = "404", description = "Restaurant or menu item not found.", ref = "#/components/responses/NotFoundResponse"),
			@ApiResponse(responseCode = "422", description = "Business rule violation.", ref = "#/components/responses/UnprocessableEntityResponse") })
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
	@Operation(summary = "Delete menu item", description = "Removes a menu item scoped by restaurant.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Menu item removed successfully."),
			@ApiResponse(responseCode = "404", description = "Restaurant or menu item not found.", ref = "#/components/responses/NotFoundResponse") })
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

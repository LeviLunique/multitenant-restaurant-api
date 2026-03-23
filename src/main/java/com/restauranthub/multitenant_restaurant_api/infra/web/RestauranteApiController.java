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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/restaurantes")
@RequiredArgsConstructor
@Tag(name = "Restaurantes", description = "Operations for restaurant management.")
public class RestauranteApiController {

	private final RestauranteController restauranteController;

	@PostMapping
	@Operation(summary = "Create restaurant", description = "Creates a restaurant associated with an owner user.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Restaurant created successfully."),
			@ApiResponse(responseCode = "400", description = "Invalid request payload.", ref = "#/components/responses/BadRequestResponse"),
			@ApiResponse(responseCode = "404", description = "Owner user not found.", ref = "#/components/responses/NotFoundResponse"),
			@ApiResponse(responseCode = "422", description = "Business rule violation.", ref = "#/components/responses/UnprocessableEntityResponse") })
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
	@Operation(summary = "Get restaurant by id", description = "Returns a restaurant by identifier.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Restaurant found."),
			@ApiResponse(responseCode = "404", description = "Restaurant not found.", ref = "#/components/responses/NotFoundResponse") })
	public RestauranteResponseJson obterPorId(@PathVariable Long id) {
		return map(restauranteController.obterPorId(id));
	}

	@GetMapping
	@Operation(summary = "List restaurants", description = "Returns all registered restaurants.")
	@ApiResponse(responseCode = "200", description = "Restaurants returned successfully.")
	public List<RestauranteResponseJson> listar() {
		return restauranteController.listar().stream().map(this::map).toList();
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update restaurant", description = "Updates a restaurant by identifier.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Restaurant updated successfully."),
			@ApiResponse(responseCode = "400", description = "Invalid request payload.", ref = "#/components/responses/BadRequestResponse"),
			@ApiResponse(responseCode = "404", description = "Restaurant or owner user not found.", ref = "#/components/responses/NotFoundResponse"),
			@ApiResponse(responseCode = "422", description = "Business rule violation.", ref = "#/components/responses/UnprocessableEntityResponse") })
	public RestauranteResponseJson atualizar(@PathVariable Long id, @Valid @RequestBody RestauranteJson restauranteJson) {
		return map(restauranteController.atualizar(id, new AtualizarRestauranteInputDto(
				restauranteJson.nome(),
				restauranteJson.endereco(),
				restauranteJson.tipoCozinha(),
				restauranteJson.horarioFuncionamento(),
				restauranteJson.donoUsuarioId())));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete restaurant", description = "Removes a restaurant by identifier.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Restaurant removed successfully."),
			@ApiResponse(responseCode = "404", description = "Restaurant not found.", ref = "#/components/responses/NotFoundResponse") })
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

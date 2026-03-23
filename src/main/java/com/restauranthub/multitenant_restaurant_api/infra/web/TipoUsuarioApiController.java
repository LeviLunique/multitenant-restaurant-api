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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tipos-usuario")
@RequiredArgsConstructor
@Tag(name = "Tipos de Usuario", description = "Operations for user type management.")
public class TipoUsuarioApiController {

	private final TipoUsuarioController tipoUsuarioController;

	@PostMapping
	@Operation(summary = "Create user type", description = "Creates a new user type.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "User type created successfully."),
			@ApiResponse(responseCode = "400", description = "Invalid request payload.", ref = "#/components/responses/BadRequestResponse"),
			@ApiResponse(responseCode = "422", description = "Business rule violation.", ref = "#/components/responses/UnprocessableEntityResponse") })
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
	@Operation(summary = "Get user type by id", description = "Returns a user type by identifier.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User type found."),
			@ApiResponse(responseCode = "404", description = "User type not found.", ref = "#/components/responses/NotFoundResponse") })
	public TipoUsuarioResponseJson obterPorId(@PathVariable Long id) {
		return map(tipoUsuarioController.obterPorId(id));
	}

	@GetMapping
	@Operation(summary = "List user types", description = "Returns all registered user types.")
	@ApiResponse(responseCode = "200", description = "User types returned successfully.")
	public List<TipoUsuarioResponseJson> listar() {
		return tipoUsuarioController.listar().stream().map(this::map).toList();
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update user type", description = "Updates a user type by identifier.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User type updated successfully."),
			@ApiResponse(responseCode = "400", description = "Invalid request payload.", ref = "#/components/responses/BadRequestResponse"),
			@ApiResponse(responseCode = "404", description = "User type not found.", ref = "#/components/responses/NotFoundResponse"),
			@ApiResponse(responseCode = "422", description = "Business rule violation.", ref = "#/components/responses/UnprocessableEntityResponse") })
	public TipoUsuarioResponseJson atualizar(@PathVariable Long id, @Valid @RequestBody TipoUsuarioJson tipoUsuarioJson) {
		return map(tipoUsuarioController.atualizar(id, new AtualizarTipoUsuarioInputDto(tipoUsuarioJson.nome(), tipoUsuarioJson.tipo())));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete user type", description = "Removes a user type by identifier.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "User type removed successfully."),
			@ApiResponse(responseCode = "404", description = "User type not found.", ref = "#/components/responses/NotFoundResponse") })
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		tipoUsuarioController.remover(id);
		return ResponseEntity.noContent().build();
	}

	private TipoUsuarioResponseJson map(TipoUsuarioOutputDto tipoUsuarioOutputDto) {
		return new TipoUsuarioResponseJson(tipoUsuarioOutputDto.id(), tipoUsuarioOutputDto.nome(), tipoUsuarioOutputDto.tipo());
	}
}

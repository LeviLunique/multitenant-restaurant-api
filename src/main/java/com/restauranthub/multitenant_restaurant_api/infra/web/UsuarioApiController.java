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

import com.restauranthub.multitenant_restaurant_api.core.controller.UsuarioController;
import com.restauranthub.multitenant_restaurant_api.core.dto.AtualizarUsuarioInputDto;
import com.restauranthub.multitenant_restaurant_api.core.dto.CriarUsuarioInputDto;
import com.restauranthub.multitenant_restaurant_api.core.dto.UsuarioOutputDto;
import com.restauranthub.multitenant_restaurant_api.infra.web.json.TipoUsuarioResponseJson;
import com.restauranthub.multitenant_restaurant_api.infra.web.json.UsuarioJson;
import com.restauranthub.multitenant_restaurant_api.infra.web.json.UsuarioResponseJson;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Operations for user management.")
public class UsuarioApiController {

	private final UsuarioController usuarioController;

	@PostMapping
	@Operation(summary = "Create user", description = "Creates a new user and returns the created resource.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "User created successfully."),
			@ApiResponse(responseCode = "400", description = "Invalid request payload.", ref = "#/components/responses/BadRequestResponse"),
			@ApiResponse(responseCode = "422", description = "Business rule violation.", ref = "#/components/responses/UnprocessableEntityResponse"),
			@ApiResponse(responseCode = "500", description = "Unexpected application error.", ref = "#/components/responses/InternalServerErrorResponse") })
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
	@Operation(summary = "Get user by id", description = "Returns a user and its associated user types.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User found."),
			@ApiResponse(responseCode = "404", description = "User not found.", ref = "#/components/responses/NotFoundResponse") })
	public UsuarioResponseJson obterPorId(@PathVariable Long id) {
		return map(usuarioController.obterPorId(id));
	}

	@GetMapping
	@Operation(summary = "List users", description = "Returns all registered users.")
	@ApiResponse(responseCode = "200", description = "Users returned successfully.")
	public List<UsuarioResponseJson> listar() {
		return usuarioController.listar().stream().map(this::map).toList();
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update user", description = "Updates the user data by identifier.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User updated successfully."),
			@ApiResponse(responseCode = "400", description = "Invalid request payload.", ref = "#/components/responses/BadRequestResponse"),
			@ApiResponse(responseCode = "404", description = "User not found.", ref = "#/components/responses/NotFoundResponse"),
			@ApiResponse(responseCode = "422", description = "Business rule violation.", ref = "#/components/responses/UnprocessableEntityResponse") })
	public UsuarioResponseJson atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioJson usuarioJson) {
		return map(usuarioController.atualizar(id, new AtualizarUsuarioInputDto(usuarioJson.nome(), usuarioJson.email())));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete user", description = "Removes a user by identifier.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "User removed successfully."),
			@ApiResponse(responseCode = "404", description = "User not found.", ref = "#/components/responses/NotFoundResponse") })
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

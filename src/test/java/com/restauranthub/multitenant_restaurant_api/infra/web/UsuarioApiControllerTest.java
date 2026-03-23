package com.restauranthub.multitenant_restaurant_api.infra.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.repository.RestauranteRepository;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.repository.UsuarioRepository;

@SpringBootTest
@AutoConfigureMockMvc
class UsuarioApiControllerTest {

	private static final String LEVI_LUNIQUE = "Levi Lunique";
	private static final String LEVI_EMAIL = "levi@example.com";
	private static final String MARIA_SILVA = "Maria Silva";
	private static final String MARIA_EMAIL = "maria@example.com";
	private static final String OUTRO_LEVI = "Outro Levi";
	private static final String LEVI_ATUALIZADO = "Levi Atualizado";
	private static final String LEVI_ATUALIZADO_EMAIL = "levi.atualizado@example.com";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	void setUp() {
		restauranteRepository.deleteAll();
		usuarioRepository.deleteAll();
	}

	@Test
	void shouldCreateUserThroughHttp() throws Exception {
		var requestBody = buildUserRequestBody(LEVI_LUNIQUE, LEVI_EMAIL);

		mockMvc.perform(post("/usuarios")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.nome").value(LEVI_LUNIQUE))
				.andExpect(jsonPath("$.email").value(LEVI_EMAIL))
				.andExpect(jsonPath("$.tiposUsuario").isEmpty());
	}

	@Test
	void shouldGetUserByIdThroughHttp() throws Exception {
		var userId = criarUsuario(LEVI_LUNIQUE, LEVI_EMAIL);

		mockMvc.perform(get("/usuarios/{id}", userId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(userId))
				.andExpect(jsonPath("$.nome").value(LEVI_LUNIQUE))
				.andExpect(jsonPath("$.email").value(LEVI_EMAIL))
				.andExpect(jsonPath("$.tiposUsuario").isEmpty());
	}

	@Test
	void shouldListUsersThroughHttp() throws Exception {
		criarUsuario(LEVI_LUNIQUE, LEVI_EMAIL);
		criarUsuario(MARIA_SILVA, MARIA_EMAIL);

		mockMvc.perform(get("/usuarios"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].nome").value(LEVI_LUNIQUE))
				.andExpect(jsonPath("$[1].nome").value(MARIA_SILVA));
	}

	@Test
	void shouldRejectDuplicatedEmailThroughHttp() throws Exception {
		criarUsuario(LEVI_LUNIQUE, LEVI_EMAIL);
		var requestBody = buildUserRequestBody(OUTRO_LEVI, LEVI_EMAIL);

		mockMvc.perform(post("/usuarios")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
				.andExpect(status().isUnprocessableEntity())
				.andExpect(jsonPath("$.code").value("USER_EMAIL_ALREADY_EXISTS"));
	}

	@Test
	void shouldUpdateUserThroughHttp() throws Exception {
		var userId = criarUsuario(LEVI_LUNIQUE, LEVI_EMAIL);
		var requestBody = buildUserRequestBody(LEVI_ATUALIZADO, LEVI_ATUALIZADO_EMAIL);

		mockMvc.perform(put("/usuarios/{id}", userId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(userId))
				.andExpect(jsonPath("$.nome").value(LEVI_ATUALIZADO))
				.andExpect(jsonPath("$.email").value(LEVI_ATUALIZADO_EMAIL));
	}

	@Test
	void shouldDeleteUserThroughHttp() throws Exception {
		var userId = criarUsuario(LEVI_LUNIQUE, LEVI_EMAIL);

		mockMvc.perform(delete("/usuarios/{id}", userId))
				.andExpect(status().isNoContent());

		mockMvc.perform(get("/usuarios/{id}", userId))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.code").value("USER_NOT_FOUND"));
	}

	private long criarUsuario(String nome, String email) throws Exception {
		var requestBody = buildUserRequestBody(nome, email);

		var content = mockMvc.perform(post("/usuarios")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
				.andExpect(status().isCreated())
				.andReturn()
				.getResponse()
				.getContentAsString();

		JsonNode responseBody = objectMapper.readTree(content);
		return responseBody.get("id").asLong();
	}

	private String buildUserRequestBody(String nome, String email) {
		return """
				{
				  "nome": "%s",
				  "email": "%s"
				}
				""".formatted(nome, email);
	}
}

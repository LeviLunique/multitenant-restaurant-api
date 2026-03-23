package com.restauranthub.multitenant_restaurant_api.infra.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuarioEnum;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.repository.TipoUsuarioRepository;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.repository.UsuarioRepository;

@SpringBootTest
@AutoConfigureMockMvc
class TipoUsuarioApiControllerTest {

	private static final String TIPOS_USUARIO_ENDPOINT = "/tipos-usuario";
	private static final String USUARIOS_ENDPOINT = "/usuarios";
	private static final String LEVI_LUNIQUE = "Levi Lunique";
	private static final String LEVI_EMAIL = "levi@example.com";
	private static final String CLIENTE = "Cliente";
	private static final String DONO_RESTAURANTE = "Dono de Restaurante";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private TipoUsuarioRepository tipoUsuarioRepository;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	void setUp() {
		usuarioRepository.deleteAll();
		tipoUsuarioRepository.deleteAll();
	}

	@Test
	void shouldCreateUserTypeThroughHttp() throws Exception {
		var requestBody = buildUserTypeRequestBody(CLIENTE, TipoUsuarioEnum.CLIENTE);

		mockMvc.perform(post(TIPOS_USUARIO_ENDPOINT)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.nome").value(CLIENTE))
				.andExpect(jsonPath("$.tipo").value(TipoUsuarioEnum.CLIENTE.name()));
	}

	@Test
	void shouldListUserTypesThroughHttp() throws Exception {
		criarTipoUsuario(CLIENTE, TipoUsuarioEnum.CLIENTE);
		criarTipoUsuario(DONO_RESTAURANTE, TipoUsuarioEnum.DONO);

		mockMvc.perform(get(TIPOS_USUARIO_ENDPOINT))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].nome").value(CLIENTE))
				.andExpect(jsonPath("$[0].tipo").value(TipoUsuarioEnum.CLIENTE.name()))
				.andExpect(jsonPath("$[1].nome").value(DONO_RESTAURANTE))
				.andExpect(jsonPath("$[1].tipo").value(TipoUsuarioEnum.DONO.name()));
	}

	@Test
	void shouldUpdateUserTypeThroughHttp() throws Exception {
		var tipoUsuarioId = criarTipoUsuario(CLIENTE, TipoUsuarioEnum.CLIENTE);
		var requestBody = buildUserTypeRequestBody(DONO_RESTAURANTE, TipoUsuarioEnum.DONO);

		mockMvc.perform(put(TIPOS_USUARIO_ENDPOINT + "/{id}", tipoUsuarioId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(tipoUsuarioId))
				.andExpect(jsonPath("$.nome").value(DONO_RESTAURANTE))
				.andExpect(jsonPath("$.tipo").value(TipoUsuarioEnum.DONO.name()));
	}

	@Test
	void shouldRemoveUserTypeThroughHttp() throws Exception {
		var tipoUsuarioId = criarTipoUsuario(CLIENTE, TipoUsuarioEnum.CLIENTE);

		mockMvc.perform(delete(TIPOS_USUARIO_ENDPOINT + "/{id}", tipoUsuarioId))
				.andExpect(status().isNoContent());
	}

	@Test
	void shouldAssociateUserTypeToUserThroughHttp() throws Exception {
		var usuarioId = criarUsuario(LEVI_LUNIQUE, LEVI_EMAIL);
		var tipoUsuarioId = criarTipoUsuario(CLIENTE, TipoUsuarioEnum.CLIENTE);

		mockMvc.perform(post(USUARIOS_ENDPOINT + "/{usuarioId}/tipos-usuario/{tipoUsuarioId}", usuarioId, tipoUsuarioId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(usuarioId))
				.andExpect(jsonPath("$.tiposUsuario[0].nome").value(CLIENTE))
				.andExpect(jsonPath("$.tiposUsuario[0].tipo").value(TipoUsuarioEnum.CLIENTE.name()));
	}

	private long criarUsuario(String nome, String email) throws Exception {
		var requestBody = """
				{
				  "nome": "%s",
				  "email": "%s"
				}
				""".formatted(nome, email);

		var content = mockMvc.perform(post(USUARIOS_ENDPOINT)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
				.andExpect(status().isCreated())
				.andReturn()
				.getResponse()
				.getContentAsString();

		JsonNode responseBody = objectMapper.readTree(content);
		return responseBody.get("id").asLong();
	}

	private long criarTipoUsuario(String nome, TipoUsuarioEnum tipo) throws Exception {
		var content = mockMvc.perform(post(TIPOS_USUARIO_ENDPOINT)
				.contentType(MediaType.APPLICATION_JSON)
				.content(buildUserTypeRequestBody(nome, tipo)))
				.andExpect(status().isCreated())
				.andReturn()
				.getResponse()
				.getContentAsString();

		JsonNode responseBody = objectMapper.readTree(content);
		return responseBody.get("id").asLong();
	}

	private String buildUserTypeRequestBody(String nome, TipoUsuarioEnum tipo) {
		return """
				{
				  "nome": "%s",
				  "tipo": "%s"
				}
				""".formatted(nome, tipo.name());
	}
}

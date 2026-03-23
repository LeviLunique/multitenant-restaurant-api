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
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.repository.ItemCardapioRepository;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.repository.RestauranteRepository;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.repository.TipoUsuarioRepository;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.repository.UsuarioRepository;

@SpringBootTest
@AutoConfigureMockMvc
class RestauranteApiControllerTest {

	private static final String RESTAURANTES_ENDPOINT = "/restaurantes";
	private static final String TIPOS_USUARIO_ENDPOINT = "/tipos-usuario";
	private static final String USUARIOS_ENDPOINT = "/usuarios";
	private static final String DONO_NOME = "Dono Principal";
	private static final String DONO_EMAIL = "dono@example.com";
	private static final String DONO_TIPO_NOME = "Dono de Restaurante";
	private static final String RESTAURANTE_NOME = "Bistrô Central";
	private static final String RESTAURANTE_ENDERECO = "Rua A, 100";
	private static final String RESTAURANTE_COZINHA = "Francesa";
	private static final String RESTAURANTE_HORARIO = "09:00-22:00";
	private static final String RESTAURANTE_NOME_ATUALIZADO = "Bistrô Atualizado";
	private static final String RESTAURANTE_ENDERECO_ATUALIZADO = "Rua B, 200";
	private static final String RESTAURANTE_COZINHA_ATUALIZADA = "Italiana";
	private static final String RESTAURANTE_HORARIO_ATUALIZADO = "10:00-23:00";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ItemCardapioRepository itemCardapioRepository;

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private TipoUsuarioRepository tipoUsuarioRepository;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	void setUp() {
		itemCardapioRepository.deleteAll();
		restauranteRepository.deleteAll();
		usuarioRepository.deleteAll();
		tipoUsuarioRepository.deleteAll();
	}

	@Test
	void shouldCreateRestaurantThroughHttp() throws Exception {
		var donoUsuarioId = criarDonoUsuario();
		var requestBody = buildRestaurantRequestBody(RESTAURANTE_NOME, RESTAURANTE_ENDERECO, RESTAURANTE_COZINHA, RESTAURANTE_HORARIO, donoUsuarioId);

		mockMvc.perform(post(RESTAURANTES_ENDPOINT)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.nome").value(RESTAURANTE_NOME))
				.andExpect(jsonPath("$.donoUsuarioId").value(donoUsuarioId));
	}

	@Test
	void shouldGetRestaurantByIdThroughHttp() throws Exception {
		var donoUsuarioId = criarDonoUsuario();
		var restauranteId = criarRestaurante(donoUsuarioId);

		mockMvc.perform(get(RESTAURANTES_ENDPOINT + "/{id}", restauranteId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(restauranteId))
				.andExpect(jsonPath("$.nome").value(RESTAURANTE_NOME))
				.andExpect(jsonPath("$.donoUsuarioId").value(donoUsuarioId));
	}

	@Test
	void shouldListRestaurantsThroughHttp() throws Exception {
		var donoUsuarioId = criarDonoUsuario();
		criarRestaurante(donoUsuarioId);

		mockMvc.perform(get(RESTAURANTES_ENDPOINT))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].nome").value(RESTAURANTE_NOME));
	}

	@Test
	void shouldUpdateRestaurantThroughHttp() throws Exception {
		var donoUsuarioId = criarDonoUsuario();
		var restauranteId = criarRestaurante(donoUsuarioId);
		var requestBody = buildRestaurantRequestBody(
				RESTAURANTE_NOME_ATUALIZADO,
				RESTAURANTE_ENDERECO_ATUALIZADO,
				RESTAURANTE_COZINHA_ATUALIZADA,
				RESTAURANTE_HORARIO_ATUALIZADO,
				donoUsuarioId);

		mockMvc.perform(put(RESTAURANTES_ENDPOINT + "/{id}", restauranteId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(restauranteId))
				.andExpect(jsonPath("$.nome").value(RESTAURANTE_NOME_ATUALIZADO))
				.andExpect(jsonPath("$.tipoCozinha").value(RESTAURANTE_COZINHA_ATUALIZADA));
	}

	@Test
	void shouldDeleteRestaurantThroughHttp() throws Exception {
		var donoUsuarioId = criarDonoUsuario();
		var restauranteId = criarRestaurante(donoUsuarioId);

		mockMvc.perform(delete(RESTAURANTES_ENDPOINT + "/{id}", restauranteId))
				.andExpect(status().isNoContent());

		mockMvc.perform(get(RESTAURANTES_ENDPOINT + "/{id}", restauranteId))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.code").value("RESTAURANT_NOT_FOUND"));
	}

	@Test
	void shouldRejectRestaurantCreationWhenOwnerHasNoOwnerType() throws Exception {
		var usuarioId = criarUsuario(DONO_NOME, DONO_EMAIL);
		var requestBody = buildRestaurantRequestBody(RESTAURANTE_NOME, RESTAURANTE_ENDERECO, RESTAURANTE_COZINHA, RESTAURANTE_HORARIO, usuarioId);

		mockMvc.perform(post(RESTAURANTES_ENDPOINT)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
				.andExpect(status().isUnprocessableEntity())
				.andExpect(jsonPath("$.code").value("INVALID_RESTAURANT_OWNER_TYPE"));
	}

	private long criarDonoUsuario() throws Exception {
		var usuarioId = criarUsuario(DONO_NOME, DONO_EMAIL);
		var tipoUsuarioId = criarTipoUsuario(DONO_TIPO_NOME, TipoUsuarioEnum.DONO);

		mockMvc.perform(post(USUARIOS_ENDPOINT + "/{usuarioId}/tipos-usuario/{tipoUsuarioId}", usuarioId, tipoUsuarioId))
				.andExpect(status().isOk());

		return usuarioId;
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
		var requestBody = """
				{
				  "nome": "%s",
				  "tipo": "%s"
				}
				""".formatted(nome, tipo.name());

		var content = mockMvc.perform(post(TIPOS_USUARIO_ENDPOINT)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
				.andExpect(status().isCreated())
				.andReturn()
				.getResponse()
				.getContentAsString();

		JsonNode responseBody = objectMapper.readTree(content);
		return responseBody.get("id").asLong();
	}

	private long criarRestaurante(long donoUsuarioId) throws Exception {
		var content = mockMvc.perform(post(RESTAURANTES_ENDPOINT)
				.contentType(MediaType.APPLICATION_JSON)
				.content(buildRestaurantRequestBody(RESTAURANTE_NOME, RESTAURANTE_ENDERECO, RESTAURANTE_COZINHA, RESTAURANTE_HORARIO, donoUsuarioId)))
				.andExpect(status().isCreated())
				.andReturn()
				.getResponse()
				.getContentAsString();

		JsonNode responseBody = objectMapper.readTree(content);
		return responseBody.get("id").asLong();
	}

	private String buildRestaurantRequestBody(
			String nome,
			String endereco,
			String tipoCozinha,
			String horarioFuncionamento,
			long donoUsuarioId) {
		return """
				{
				  "nome": "%s",
				  "endereco": "%s",
				  "tipoCozinha": "%s",
				  "horarioFuncionamento": "%s",
				  "donoUsuarioId": %d
				}
				""".formatted(nome, endereco, tipoCozinha, horarioFuncionamento, donoUsuarioId);
	}
}

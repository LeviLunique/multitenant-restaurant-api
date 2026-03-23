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
class ItemCardapioApiControllerTest {

	private static final String RESTAURANTES_ENDPOINT = "/restaurantes";
	private static final String TIPOS_USUARIO_ENDPOINT = "/tipos-usuario";
	private static final String USUARIOS_ENDPOINT = "/usuarios";
	private static final String DONO_NOME = "Dono Principal";
	private static final String DONO_TIPO_NOME = "Dono de Restaurante";
	private static final String RESTAURANTE_NOME = "Bistrô Central";
	private static final String RESTAURANTE_ENDERECO = "Rua A, 100";
	private static final String RESTAURANTE_COZINHA = "Francesa";
	private static final String RESTAURANTE_HORARIO = "09:00-22:00";
	private static final String ITEM_NOME = "Risoto";
	private static final String ITEM_DESCRICAO = "Risoto de cogumelos";
	private static final String ITEM_PRECO = "49.90";
	private static final String ITEM_CAMINHO_FOTO = "/fotos/risoto.jpg";
	private static final String ITEM_NOME_ATUALIZADO = "Risoto Atualizado";
	private static final String ITEM_DESCRICAO_ATUALIZADA = "Risoto trufado";
	private static final String ITEM_PRECO_ATUALIZADO = "59.90";
	private static final String ITEM_CAMINHO_FOTO_ATUALIZADO = "/fotos/risoto-atualizado.jpg";

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
	void shouldCreateMenuItemThroughHttp() throws Exception {
		var restauranteId = criarRestaurante("Principal", "dono.principal@example.com");

		mockMvc.perform(post(itensEndpoint(restauranteId))
				.contentType(MediaType.APPLICATION_JSON)
				.content(buildMenuItemRequestBody(ITEM_NOME, ITEM_DESCRICAO, ITEM_PRECO, true, ITEM_CAMINHO_FOTO)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.restauranteId").value(restauranteId))
				.andExpect(jsonPath("$.nome").value(ITEM_NOME));
	}

	@Test
	void shouldGetMenuItemByIdThroughHttp() throws Exception {
		var restauranteId = criarRestaurante("Principal", "dono.principal@example.com");
		var itemId = criarItemCardapio(restauranteId);

		mockMvc.perform(get(itensEndpoint(restauranteId) + "/{id}", itemId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(itemId))
				.andExpect(jsonPath("$.restauranteId").value(restauranteId))
				.andExpect(jsonPath("$.nome").value(ITEM_NOME));
	}

	@Test
	void shouldListMenuItemsByRestaurantThroughHttp() throws Exception {
		var restauranteId = criarRestaurante("Principal", "dono.principal@example.com");
		criarItemCardapio(restauranteId);

		mockMvc.perform(get(itensEndpoint(restauranteId)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].nome").value(ITEM_NOME));
	}

	@Test
	void shouldUpdateMenuItemThroughHttp() throws Exception {
		var restauranteId = criarRestaurante("Principal", "dono.principal@example.com");
		var itemId = criarItemCardapio(restauranteId);

		mockMvc.perform(put(itensEndpoint(restauranteId) + "/{id}", itemId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(buildMenuItemRequestBody(
						ITEM_NOME_ATUALIZADO,
						ITEM_DESCRICAO_ATUALIZADA,
						ITEM_PRECO_ATUALIZADO,
						false,
						ITEM_CAMINHO_FOTO_ATUALIZADO)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(itemId))
				.andExpect(jsonPath("$.nome").value(ITEM_NOME_ATUALIZADO))
				.andExpect(jsonPath("$.apenasConsumoNoLocal").value(false));
	}

	@Test
	void shouldDeleteMenuItemThroughHttp() throws Exception {
		var restauranteId = criarRestaurante("Principal", "dono.principal@example.com");
		var itemId = criarItemCardapio(restauranteId);

		mockMvc.perform(delete(itensEndpoint(restauranteId) + "/{id}", itemId))
				.andExpect(status().isNoContent());

		mockMvc.perform(get(itensEndpoint(restauranteId) + "/{id}", itemId))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.code").value("MENU_ITEM_NOT_FOUND"));
	}

	@Test
	void shouldRejectCrossRestaurantAccess() throws Exception {
		var restauranteId = criarRestaurante("Principal", "dono.principal@example.com");
		var outroRestauranteId = criarRestaurante("Secundario", "dono.secundario@example.com");
		var itemId = criarItemCardapio(restauranteId);

		mockMvc.perform(get(itensEndpoint(outroRestauranteId) + "/{id}", itemId))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.code").value("MENU_ITEM_NOT_FOUND"));
	}

	private String itensEndpoint(long restauranteId) {
		return RESTAURANTES_ENDPOINT + "/%d/itens-cardapio".formatted(restauranteId);
	}

	private long criarRestaurante(String sufixoNome, String email) throws Exception {
		var donoUsuarioId = criarDonoUsuario(sufixoNome, email);
		var requestBody = """
				{
				  "nome": "%s",
				  "endereco": "%s",
				  "tipoCozinha": "%s",
				  "horarioFuncionamento": "%s",
				  "donoUsuarioId": %d
				}
				""".formatted(RESTAURANTE_NOME, RESTAURANTE_ENDERECO, RESTAURANTE_COZINHA, RESTAURANTE_HORARIO, donoUsuarioId);

		var content = mockMvc.perform(post(RESTAURANTES_ENDPOINT)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
				.andExpect(status().isCreated())
				.andReturn()
				.getResponse()
				.getContentAsString();

		JsonNode responseBody = objectMapper.readTree(content);
		return responseBody.get("id").asLong();
	}

	private long criarItemCardapio(long restauranteId) throws Exception {
		var content = mockMvc.perform(post(itensEndpoint(restauranteId))
				.contentType(MediaType.APPLICATION_JSON)
				.content(buildMenuItemRequestBody(ITEM_NOME, ITEM_DESCRICAO, ITEM_PRECO, true, ITEM_CAMINHO_FOTO)))
				.andExpect(status().isCreated())
				.andReturn()
				.getResponse()
				.getContentAsString();

		JsonNode responseBody = objectMapper.readTree(content);
		return responseBody.get("id").asLong();
	}

	private long criarDonoUsuario(String sufixoNome, String email) throws Exception {
		var usuarioId = criarUsuario(DONO_NOME + " " + sufixoNome, email);
		var tipoUsuarioId = tipoUsuarioRepository.findByTipo(TipoUsuarioEnum.DONO)
				.map(com.restauranthub.multitenant_restaurant_api.infra.database.jpa.entity.TipoUsuarioEntity::getId)
				.orElseGet(() -> {
					try {
						return criarTipoUsuario(DONO_TIPO_NOME, TipoUsuarioEnum.DONO);
					} catch (Exception exception) {
						throw new IllegalStateException(exception);
					}
				});

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

	private String buildMenuItemRequestBody(
			String nome,
			String descricao,
			String preco,
			boolean apenasConsumoNoLocal,
			String caminhoFoto) {
		return """
				{
				  "nome": "%s",
				  "descricao": "%s",
				  "preco": %s,
				  "apenasConsumoNoLocal": %s,
				  "caminhoFoto": "%s"
				}
				""".formatted(nome, descricao, preco, apenasConsumoNoLocal, caminhoFoto);
	}
}

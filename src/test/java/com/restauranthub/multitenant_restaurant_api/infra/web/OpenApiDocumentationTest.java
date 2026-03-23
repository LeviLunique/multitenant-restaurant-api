package com.restauranthub.multitenant_restaurant_api.infra.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class OpenApiDocumentationTest {

	private static final String API_DOCS_ENDPOINT = "/v3/api-docs";
	private static final String SWAGGER_UI_ENDPOINT = "/swagger-ui/index.html";

	@Autowired
	private MockMvc mockMvc;

	@Test
	void shouldExposeOpenApiDocumentation() throws Exception {
		mockMvc.perform(get(API_DOCS_ENDPOINT))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.info.title").value("Restaurant Hub API"))
				.andExpect(jsonPath("$.paths['/usuarios']").exists())
				.andExpect(jsonPath("$.paths['/tipos-usuario']").exists())
				.andExpect(jsonPath("$.paths['/restaurantes']").exists())
				.andExpect(jsonPath("$.paths['/restaurantes/{restauranteId}/itens-cardapio']").exists());
	}

	@Test
	void shouldExposeSwaggerUi() throws Exception {
		mockMvc.perform(get(SWAGGER_UI_ENDPOINT))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("Swagger UI")));
	}
}

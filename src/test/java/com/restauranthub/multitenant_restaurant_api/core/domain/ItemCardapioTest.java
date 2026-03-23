package com.restauranthub.multitenant_restaurant_api.core.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.restauranthub.multitenant_restaurant_api.core.exception.BusinessException;

class ItemCardapioTest {

	private static final Long ITEM_ID = 1L;
	private static final Long RESTAURANTE_ID = 10L;
	private static final String ITEM_NOME = "Risoto";
	private static final String ITEM_DESCRICAO = "Descricao";
	private static final BigDecimal ITEM_PRECO = new BigDecimal("49.90");
	private static final String ITEM_CAMINHO_FOTO = "/foto.jpg";

	@Test
	void shouldCreateMenuItemWithValidData() {
		var itemCardapio = new ItemCardapio(1L, 10L, "Risoto", "Risoto de cogumelos", new BigDecimal("49.90"), true, "/fotos/risoto.jpg");

		assertEquals(1L, itemCardapio.getId());
		assertEquals(10L, itemCardapio.getRestauranteId());
		assertEquals("Risoto", itemCardapio.getNome());
		assertEquals("Risoto de cogumelos", itemCardapio.getDescricao());
		assertEquals(new BigDecimal("49.90"), itemCardapio.getPreco());
		assertEquals(true, itemCardapio.getApenasConsumoNoLocal());
		assertEquals("/fotos/risoto.jpg", itemCardapio.getCaminhoFoto());
	}

	@Test
	void shouldRejectBlankName() {
		var nomeInvalido = " ";

		assertThrows(BusinessException.class, () -> criarItemCardapio(nomeInvalido, ITEM_DESCRICAO, ITEM_PRECO, true, ITEM_CAMINHO_FOTO, RESTAURANTE_ID));
	}

	@Test
	void shouldRejectBlankDescription() {
		var descricaoInvalida = " ";

		assertThrows(BusinessException.class, () -> criarItemCardapio(ITEM_NOME, descricaoInvalida, ITEM_PRECO, true, ITEM_CAMINHO_FOTO, RESTAURANTE_ID));
	}

	@Test
	void shouldRejectInvalidPrice() {
		var precoInvalido = BigDecimal.ZERO;

		assertThrows(BusinessException.class, () -> criarItemCardapio(ITEM_NOME, ITEM_DESCRICAO, precoInvalido, true, ITEM_CAMINHO_FOTO, RESTAURANTE_ID));
	}

	@Test
	void shouldRejectNullLocalConsumptionAvailability() {
		assertThrows(BusinessException.class, () -> criarItemCardapio(ITEM_NOME, ITEM_DESCRICAO, ITEM_PRECO, null, ITEM_CAMINHO_FOTO, RESTAURANTE_ID));
	}

	@Test
	void shouldRejectBlankPhotoPath() {
		var caminhoFotoInvalido = " ";

		assertThrows(BusinessException.class, () -> criarItemCardapio(ITEM_NOME, ITEM_DESCRICAO, ITEM_PRECO, true, caminhoFotoInvalido, RESTAURANTE_ID));
	}

	@Test
	void shouldRejectInvalidRestaurantId() {
		var restauranteIdInvalido = 0L;

		assertThrows(BusinessException.class, () -> criarItemCardapio(ITEM_NOME, ITEM_DESCRICAO, ITEM_PRECO, true, ITEM_CAMINHO_FOTO, restauranteIdInvalido));
	}

	private ItemCardapio criarItemCardapio(
			String nome,
			String descricao,
			BigDecimal preco,
			Boolean apenasConsumoNoLocal,
			String caminhoFoto,
			Long restauranteId) {
		return new ItemCardapio(ITEM_ID, restauranteId, nome, descricao, preco, apenasConsumoNoLocal, caminhoFoto);
	}
}

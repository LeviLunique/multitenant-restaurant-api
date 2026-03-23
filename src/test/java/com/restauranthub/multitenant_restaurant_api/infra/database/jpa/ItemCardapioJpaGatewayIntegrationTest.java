package com.restauranthub.multitenant_restaurant_api.infra.database.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.restauranthub.multitenant_restaurant_api.core.domain.ItemCardapio;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.entity.ItemCardapioEntity;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.entity.RestauranteEntity;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.entity.UsuarioEntity;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.repository.ItemCardapioRepository;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.repository.RestauranteRepository;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.repository.UsuarioRepository;
import com.restauranthub.multitenant_restaurant_api.infra.database.mapper.ItemCardapioEntityMapper;

@DataJpaTest
@Import({ ItemCardapioJpaGateway.class, ItemCardapioEntityMapper.class })
class ItemCardapioJpaGatewayIntegrationTest {

	@Autowired
	private ItemCardapioJpaGateway gateway;

	@Autowired
	private ItemCardapioRepository itemCardapioRepository;

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Test
	void shouldCreateMenuItem() {
		var restaurante = criarRestaurante("Bistro");

		var id = gateway.criar(new ItemCardapio(null, restaurante.getId(), "Risoto", "Descricao", new BigDecimal("49.90"), true, "/foto.jpg"));

		var savedEntity = itemCardapioRepository.findById(id);

		assertTrue(savedEntity.isPresent());
		assertEquals("Risoto", savedEntity.get().getNome());
	}

	@Test
	void shouldFindMenuItemByIdAndRestaurant() {
		var restaurante = criarRestaurante("Bistro");
		var entity = itemCardapioRepository.save(
				new ItemCardapioEntity(null, "Risoto", "Descricao", new BigDecimal("49.90"), true, "/foto.jpg", restaurante));

		var itemCardapio = gateway.obterPorId(restaurante.getId(), entity.getId());

		assertTrue(itemCardapio.isPresent());
		assertEquals(entity.getId(), itemCardapio.get().getId());
	}

	@Test
	void shouldListMenuItemsByRestaurant() {
		var restaurante = criarRestaurante("Bistro");
		itemCardapioRepository.save(new ItemCardapioEntity(null, "Risoto", "Descricao 1", new BigDecimal("49.90"), true, "/foto-1.jpg", restaurante));
		itemCardapioRepository.save(new ItemCardapioEntity(null, "Massa", "Descricao 2", new BigDecimal("39.90"), false, "/foto-2.jpg", restaurante));

		var itens = gateway.listarPorRestaurante(restaurante.getId());

		assertEquals(2, itens.size());
	}

	@Test
	void shouldUpdateMenuItem() {
		var restaurante = criarRestaurante("Bistro");
		var entity = itemCardapioRepository.save(
				new ItemCardapioEntity(null, "Risoto", "Descricao", new BigDecimal("49.90"), true, "/foto.jpg", restaurante));

		var itemCardapio = gateway.atualizar(
				new ItemCardapio(entity.getId(), restaurante.getId(), "Risoto Atualizado", "Descricao nova", new BigDecimal("54.90"), false, "/foto-nova.jpg"));

		assertEquals(entity.getId(), itemCardapio.getId());
		assertEquals("Risoto Atualizado", itemCardapioRepository.findById(entity.getId()).orElseThrow().getNome());
	}

	@Test
	void shouldRemoveMenuItem() {
		var restaurante = criarRestaurante("Bistro");
		var entity = itemCardapioRepository.save(
				new ItemCardapioEntity(null, "Risoto", "Descricao", new BigDecimal("49.90"), true, "/foto.jpg", restaurante));

		gateway.remover(restaurante.getId(), entity.getId());

		assertTrue(itemCardapioRepository.findById(entity.getId()).isEmpty());
	}

	private RestauranteEntity criarRestaurante(String nome) {
		var dono = usuarioRepository.save(new UsuarioEntity(null, "Dono", "dono@example.com"));
		return restauranteRepository.save(new RestauranteEntity(null, nome, "Rua A, 100", "Francesa", "09:00-22:00", dono));
	}
}

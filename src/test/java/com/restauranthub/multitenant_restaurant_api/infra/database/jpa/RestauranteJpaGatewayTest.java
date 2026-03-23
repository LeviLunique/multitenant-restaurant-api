package com.restauranthub.multitenant_restaurant_api.infra.database.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.restauranthub.multitenant_restaurant_api.core.domain.Restaurante;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.entity.RestauranteEntity;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.entity.UsuarioEntity;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.repository.RestauranteRepository;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.repository.UsuarioRepository;
import com.restauranthub.multitenant_restaurant_api.infra.database.mapper.RestauranteEntityMapper;

@DataJpaTest
@Import({ RestauranteJpaGateway.class, RestauranteEntityMapper.class })
class RestauranteJpaGatewayTest {

	@Autowired
	private RestauranteJpaGateway gateway;

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Test
	void shouldCreateRestaurant() {
		var dono = usuarioRepository.save(new UsuarioEntity(null, "Dono", "dono@example.com"));

		var id = gateway.criar(new Restaurante(null, "Bistrô Central", "Rua A, 100", "Francesa", "09:00-22:00", dono.getId()));

		var savedEntity = restauranteRepository.findById(id);

		assertTrue(savedEntity.isPresent());
		assertEquals("Bistrô Central", savedEntity.get().getNome());
	}

	@Test
	void shouldFindRestaurantById() {
		var dono = usuarioRepository.save(new UsuarioEntity(null, "Dono", "dono@example.com"));
		var entity = restauranteRepository.save(new RestauranteEntity(null, "Bistrô Central", "Rua A, 100", "Francesa", "09:00-22:00", dono));

		var restaurante = gateway.obterPorId(entity.getId());

		assertTrue(restaurante.isPresent());
		assertEquals(entity.getId(), restaurante.get().getId());
	}

	@Test
	void shouldListRestaurants() {
		var donoA = usuarioRepository.save(new UsuarioEntity(null, "Dono A", "dono.a@example.com"));
		var donoB = usuarioRepository.save(new UsuarioEntity(null, "Dono B", "dono.b@example.com"));
		restauranteRepository.save(new RestauranteEntity(null, "Bistrô Central", "Rua A, 100", "Francesa", "09:00-22:00", donoA));
		restauranteRepository.save(new RestauranteEntity(null, "Cantina Sul", "Rua B, 200", "Italiana", "10:00-23:00", donoB));

		var restaurantes = gateway.listar();

		assertEquals(2, restaurantes.size());
	}

	@Test
	void shouldUpdateRestaurant() {
		var donoA = usuarioRepository.save(new UsuarioEntity(null, "Dono A", "dono.a@example.com"));
		var donoB = usuarioRepository.save(new UsuarioEntity(null, "Dono B", "dono.b@example.com"));
		var entity = restauranteRepository.save(new RestauranteEntity(null, "Bistrô Central", "Rua A, 100", "Francesa", "09:00-22:00", donoA));

		var restaurante = gateway.atualizar(new Restaurante(entity.getId(), "Bistrô Atualizado", "Rua B, 200", "Italiana", "10:00-23:00", donoB.getId()));

		assertEquals(entity.getId(), restaurante.getId());
		assertEquals("Bistrô Atualizado", restauranteRepository.findById(entity.getId()).orElseThrow().getNome());
	}

	@Test
	void shouldRemoveRestaurant() {
		var dono = usuarioRepository.save(new UsuarioEntity(null, "Dono", "dono@example.com"));
		var entity = restauranteRepository.save(new RestauranteEntity(null, "Bistrô Central", "Rua A, 100", "Francesa", "09:00-22:00", dono));

		gateway.remover(entity.getId());

		assertTrue(restauranteRepository.findById(entity.getId()).isEmpty());
	}
}

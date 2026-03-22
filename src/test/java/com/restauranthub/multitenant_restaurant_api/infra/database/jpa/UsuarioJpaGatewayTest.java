package com.restauranthub.multitenant_restaurant_api.infra.database.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.restauranthub.multitenant_restaurant_api.core.domain.Usuario;
import com.restauranthub.multitenant_restaurant_api.infra.database.mapper.UsuarioEntityMapper;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.entity.UsuarioEntity;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.repository.UsuarioRepository;

@DataJpaTest
@Import({ UsuarioJpaGateway.class, UsuarioEntityMapper.class })
class UsuarioJpaGatewayTest {

	@Autowired
	private UsuarioJpaGateway gateway;

	@Autowired
	private UsuarioRepository repository;

	@Test
	void shouldCreateUser() {
		var id = gateway.criar(new Usuario(null, "Levi Lunique", "levi@example.com"));

		var savedEntity = repository.findById(id);

		assertTrue(savedEntity.isPresent());
		assertEquals("Levi Lunique", savedEntity.get().getNome());
	}

	@Test
	void shouldFindUserById() {
		var entity = repository.save(new UsuarioEntity(null, "Levi Lunique", "levi@example.com"));

		var usuario = gateway.obterPorId(entity.getId());

		assertTrue(usuario.isPresent());
		assertEquals(entity.getId(), usuario.get().getId());
	}

	@Test
	void shouldFindUserByEmail() {
		repository.save(new UsuarioEntity(null, "Levi Lunique", "levi@example.com"));

		var usuario = gateway.obterPorEmail("levi@example.com");

		assertTrue(usuario.isPresent());
		assertEquals("levi@example.com", usuario.get().getEmail());
	}

	@Test
	void shouldListUsers() {
		repository.save(new UsuarioEntity(null, "Levi Lunique", "levi@example.com"));
		repository.save(new UsuarioEntity(null, "Maria Silva", "maria@example.com"));

		var usuarios = gateway.listar();

		assertEquals(2, usuarios.size());
	}
}

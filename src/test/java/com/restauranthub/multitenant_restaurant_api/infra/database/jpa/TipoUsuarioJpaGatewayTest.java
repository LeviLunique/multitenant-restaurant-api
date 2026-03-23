package com.restauranthub.multitenant_restaurant_api.infra.database.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuario;
import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuarioEnum;
import com.restauranthub.multitenant_restaurant_api.infra.database.mapper.TipoUsuarioEntityMapper;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.entity.TipoUsuarioEntity;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.repository.TipoUsuarioRepository;

@DataJpaTest
@Import({ TipoUsuarioJpaGateway.class, TipoUsuarioEntityMapper.class })
class TipoUsuarioJpaGatewayTest {

	@Autowired
	private TipoUsuarioJpaGateway gateway;

	@Autowired
	private TipoUsuarioRepository repository;

	@Test
	void shouldCreateUserType() {
		var id = gateway.criar(new TipoUsuario(null, "Cliente", TipoUsuarioEnum.CLIENTE));

		var savedEntity = repository.findById(id);

		assertTrue(savedEntity.isPresent());
		assertEquals("Cliente", savedEntity.get().getNome());
		assertEquals(TipoUsuarioEnum.CLIENTE, savedEntity.get().getTipo());
	}

	@Test
	void shouldFindUserTypeById() {
		var entity = repository.save(new TipoUsuarioEntity(null, "Cliente", TipoUsuarioEnum.CLIENTE));

		var tipoUsuario = gateway.obterPorId(entity.getId());

		assertTrue(tipoUsuario.isPresent());
		assertEquals(entity.getId(), tipoUsuario.get().getId());
	}

	@Test
	void shouldFindUserTypeByName() {
		repository.save(new TipoUsuarioEntity(null, "Cliente", TipoUsuarioEnum.CLIENTE));

		var tipoUsuario = gateway.obterPorNome("Cliente");

		assertTrue(tipoUsuario.isPresent());
		assertEquals("Cliente", tipoUsuario.get().getNome());
	}

	@Test
	void shouldFindUserTypeByEnum() {
		repository.save(new TipoUsuarioEntity(null, "Cliente", TipoUsuarioEnum.CLIENTE));

		var tipoUsuario = gateway.obterPorTipo(TipoUsuarioEnum.CLIENTE);

		assertTrue(tipoUsuario.isPresent());
		assertEquals(TipoUsuarioEnum.CLIENTE, tipoUsuario.get().getTipo());
	}

	@Test
	void shouldListUserTypes() {
		repository.save(new TipoUsuarioEntity(null, "Cliente", TipoUsuarioEnum.CLIENTE));
		repository.save(new TipoUsuarioEntity(null, "Dono de Restaurante", TipoUsuarioEnum.DONO));

		var tiposUsuario = gateway.listar();

		assertEquals(2, tiposUsuario.size());
	}

	@Test
	void shouldUpdateUserType() {
		var entity = repository.save(new TipoUsuarioEntity(null, "Cliente", TipoUsuarioEnum.CLIENTE));

		var tipoUsuario = gateway.atualizar(new TipoUsuario(entity.getId(), "Dono de Restaurante", TipoUsuarioEnum.DONO));

		assertEquals(entity.getId(), tipoUsuario.getId());
		assertEquals("Dono de Restaurante", repository.findById(entity.getId()).orElseThrow().getNome());
		assertEquals(TipoUsuarioEnum.DONO, repository.findById(entity.getId()).orElseThrow().getTipo());
	}

	@Test
	void shouldRemoveUserType() {
		var entity = repository.save(new TipoUsuarioEntity(null, "Cliente", TipoUsuarioEnum.CLIENTE));

		gateway.remover(entity.getId());

		assertTrue(repository.findById(entity.getId()).isEmpty());
	}
}

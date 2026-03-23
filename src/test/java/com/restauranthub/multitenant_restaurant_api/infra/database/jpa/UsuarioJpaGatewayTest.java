package com.restauranthub.multitenant_restaurant_api.infra.database.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.restauranthub.multitenant_restaurant_api.core.domain.Usuario;
import com.restauranthub.multitenant_restaurant_api.core.exception.InfrastructureException;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.entity.UsuarioEntity;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.repository.UsuarioRepository;
import com.restauranthub.multitenant_restaurant_api.infra.database.mapper.UsuarioEntityMapper;

@ExtendWith(MockitoExtension.class)
class UsuarioJpaGatewayTest {

	private static final String ERROR_CODE = "USER_REPOSITORY_ERROR";

	@Mock
	private UsuarioRepository usuarioRepository;

	private UsuarioJpaGateway gateway;

	@BeforeEach
	void setUp() {
		gateway = new UsuarioJpaGateway(usuarioRepository, new UsuarioEntityMapper());
	}

	@Test
	void shouldNormalizeEmailWhenQueryingUserByEmail() {
		var usuarioEntity = new UsuarioEntity(1L, "Levi", "levi@example.com");

		when(usuarioRepository.findByEmail("levi@example.com")).thenReturn(Optional.of(usuarioEntity));

		var resultado = gateway.obterPorEmail("LeVi@Example.Com");

		assertTrue(resultado.isPresent());
		assertEquals("levi@example.com", resultado.get().getEmail());
	}

	@Test
	void shouldReturnEmptyWhenUserIsNotFoundById() {
		when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

		var resultado = gateway.obterPorId(99L);

		assertTrue(resultado.isEmpty());
	}

	@Test
	void shouldWrapRepositoryErrorWhenCreatingUser() {
		var usuario = new Usuario(null, "Levi", "levi@example.com");

		when(usuarioRepository.save(any(UsuarioEntity.class))).thenThrow(new RuntimeException("boom"));

		var exception = assertThrows(InfrastructureException.class, () -> gateway.criar(usuario));

		assertEquals(ERROR_CODE, exception.getCode());
		assertEquals("Could not persist user.", exception.getMessage());
	}

	@Test
	void shouldWrapRepositoryErrorWhenFindingUserById() {
		when(usuarioRepository.findById(1L)).thenThrow(new RuntimeException("boom"));

		var exception = assertThrows(InfrastructureException.class, () -> gateway.obterPorId(1L));

		assertEquals(ERROR_CODE, exception.getCode());
		assertEquals("Could not query user by id.", exception.getMessage());
	}

	@Test
	void shouldWrapRepositoryErrorWhenFindingUserByEmail() {
		when(usuarioRepository.findByEmail("levi@example.com")).thenThrow(new RuntimeException("boom"));

		var exception = assertThrows(InfrastructureException.class, () -> gateway.obterPorEmail("levi@example.com"));

		assertEquals(ERROR_CODE, exception.getCode());
		assertEquals("Could not query user by email.", exception.getMessage());
	}

	@Test
	void shouldWrapRepositoryErrorWhenListingUsers() {
		when(usuarioRepository.findAllByOrderByIdAsc()).thenThrow(new RuntimeException("boom"));

		var exception = assertThrows(InfrastructureException.class, gateway::listar);

		assertEquals(ERROR_CODE, exception.getCode());
		assertEquals("Could not list users.", exception.getMessage());
	}

	@Test
	void shouldWrapRepositoryErrorWhenUpdatingUser() {
		var usuario = new Usuario(1L, "Levi", "levi@example.com");

		when(usuarioRepository.save(any(UsuarioEntity.class))).thenThrow(new RuntimeException("boom"));

		var exception = assertThrows(InfrastructureException.class, () -> gateway.atualizar(usuario));

		assertEquals(ERROR_CODE, exception.getCode());
		assertEquals("Could not update user.", exception.getMessage());
	}

	@Test
	void shouldWrapRepositoryErrorWhenRemovingUser() {
		doThrow(new RuntimeException("boom")).when(usuarioRepository).deleteById(1L);

		var exception = assertThrows(InfrastructureException.class, () -> gateway.remover(1L));

		assertEquals(ERROR_CODE, exception.getCode());
		assertEquals("Could not remove user.", exception.getMessage());
	}
}

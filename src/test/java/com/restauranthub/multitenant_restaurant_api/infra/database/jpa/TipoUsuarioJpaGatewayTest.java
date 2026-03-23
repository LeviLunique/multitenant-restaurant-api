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

import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuario;
import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuarioEnum;
import com.restauranthub.multitenant_restaurant_api.core.exception.InfrastructureException;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.entity.TipoUsuarioEntity;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.repository.TipoUsuarioRepository;
import com.restauranthub.multitenant_restaurant_api.infra.database.mapper.TipoUsuarioEntityMapper;

@ExtendWith(MockitoExtension.class)
class TipoUsuarioJpaGatewayTest {

	private static final String ERROR_CODE = "USER_TYPE_REPOSITORY_ERROR";

	@Mock
	private TipoUsuarioRepository tipoUsuarioRepository;

	private TipoUsuarioJpaGateway gateway;

	@BeforeEach
	void setUp() {
		gateway = new TipoUsuarioJpaGateway(tipoUsuarioRepository, new TipoUsuarioEntityMapper());
	}

	@Test
	void shouldReturnEmptyWhenUserTypeIsNotFoundByEnum() {
		when(tipoUsuarioRepository.findByTipo(TipoUsuarioEnum.CLIENTE)).thenReturn(Optional.empty());

		var resultado = gateway.obterPorTipo(TipoUsuarioEnum.CLIENTE);

		assertTrue(resultado.isEmpty());
	}

	@Test
	void shouldWrapRepositoryErrorWhenCreatingUserType() {
		var tipoUsuario = new TipoUsuario(null, "Cliente", TipoUsuarioEnum.CLIENTE);

		when(tipoUsuarioRepository.save(any(TipoUsuarioEntity.class))).thenThrow(new RuntimeException("boom"));

		var exception = assertThrows(InfrastructureException.class, () -> gateway.criar(tipoUsuario));

		assertEquals(ERROR_CODE, exception.getCode());
		assertEquals("Could not persist user type.", exception.getMessage());
	}

	@Test
	void shouldWrapRepositoryErrorWhenFindingUserTypeById() {
		when(tipoUsuarioRepository.findById(1L)).thenThrow(new RuntimeException("boom"));

		var exception = assertThrows(InfrastructureException.class, () -> gateway.obterPorId(1L));

		assertEquals(ERROR_CODE, exception.getCode());
		assertEquals("Could not query user type by id.", exception.getMessage());
	}

	@Test
	void shouldWrapRepositoryErrorWhenFindingUserTypeByName() {
		when(tipoUsuarioRepository.findByNome("Cliente")).thenThrow(new RuntimeException("boom"));

		var exception = assertThrows(InfrastructureException.class, () -> gateway.obterPorNome("Cliente"));

		assertEquals(ERROR_CODE, exception.getCode());
		assertEquals("Could not query user type by name.", exception.getMessage());
	}

	@Test
	void shouldWrapRepositoryErrorWhenFindingUserTypeByEnum() {
		when(tipoUsuarioRepository.findByTipo(TipoUsuarioEnum.CLIENTE)).thenThrow(new RuntimeException("boom"));

		var exception = assertThrows(InfrastructureException.class, () -> gateway.obterPorTipo(TipoUsuarioEnum.CLIENTE));

		assertEquals(ERROR_CODE, exception.getCode());
		assertEquals("Could not query user type by enum.", exception.getMessage());
	}

	@Test
	void shouldWrapRepositoryErrorWhenListingUserTypes() {
		when(tipoUsuarioRepository.findAllByOrderByIdAsc()).thenThrow(new RuntimeException("boom"));

		var exception = assertThrows(InfrastructureException.class, gateway::listar);

		assertEquals(ERROR_CODE, exception.getCode());
		assertEquals("Could not list user types.", exception.getMessage());
	}

	@Test
	void shouldWrapRepositoryErrorWhenUpdatingUserType() {
		var tipoUsuario = new TipoUsuario(1L, "Cliente", TipoUsuarioEnum.CLIENTE);

		when(tipoUsuarioRepository.save(any(TipoUsuarioEntity.class))).thenThrow(new RuntimeException("boom"));

		var exception = assertThrows(InfrastructureException.class, () -> gateway.atualizar(tipoUsuario));

		assertEquals(ERROR_CODE, exception.getCode());
		assertEquals("Could not update user type.", exception.getMessage());
	}

	@Test
	void shouldWrapRepositoryErrorWhenRemovingUserType() {
		doThrow(new RuntimeException("boom")).when(tipoUsuarioRepository).deleteById(1L);

		var exception = assertThrows(InfrastructureException.class, () -> gateway.remover(1L));

		assertEquals(ERROR_CODE, exception.getCode());
		assertEquals("Could not remove user type.", exception.getMessage());
	}
}

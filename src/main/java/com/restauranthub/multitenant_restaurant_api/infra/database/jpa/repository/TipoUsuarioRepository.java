package com.restauranthub.multitenant_restaurant_api.infra.database.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuarioEnum;
import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.entity.TipoUsuarioEntity;

public interface TipoUsuarioRepository extends JpaRepository<TipoUsuarioEntity, Long> {

	Optional<TipoUsuarioEntity> findByNome(String nome);

	Optional<TipoUsuarioEntity> findByTipo(TipoUsuarioEnum tipo);

	List<TipoUsuarioEntity> findAllByOrderByIdAsc();
}

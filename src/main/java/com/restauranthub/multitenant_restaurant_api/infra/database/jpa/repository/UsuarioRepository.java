package com.restauranthub.multitenant_restaurant_api.infra.database.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.entity.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

	Optional<UsuarioEntity> findByEmail(String email);
}

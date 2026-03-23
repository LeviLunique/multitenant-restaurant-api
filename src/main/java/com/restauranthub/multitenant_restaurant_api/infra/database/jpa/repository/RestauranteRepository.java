package com.restauranthub.multitenant_restaurant_api.infra.database.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.entity.RestauranteEntity;

public interface RestauranteRepository extends JpaRepository<RestauranteEntity, Long> {

	List<RestauranteEntity> findAllByOrderByIdAsc();
}

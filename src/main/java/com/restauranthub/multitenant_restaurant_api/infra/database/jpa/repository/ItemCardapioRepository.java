package com.restauranthub.multitenant_restaurant_api.infra.database.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restauranthub.multitenant_restaurant_api.infra.database.jpa.entity.ItemCardapioEntity;

public interface ItemCardapioRepository extends JpaRepository<ItemCardapioEntity, Long> {

	Optional<ItemCardapioEntity> findByIdAndRestauranteId(Long id, Long restauranteId);

	List<ItemCardapioEntity> findAllByRestauranteIdOrderByIdAsc(Long restauranteId);
}

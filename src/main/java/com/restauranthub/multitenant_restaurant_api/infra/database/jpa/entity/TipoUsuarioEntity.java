package com.restauranthub.multitenant_restaurant_api.infra.database.jpa.entity;

import com.restauranthub.multitenant_restaurant_api.core.domain.TipoUsuarioEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tipos_usuario", schema = "restaurant_hub")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TipoUsuarioEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 100)
	private String nome;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, unique = true, length = 30)
	private TipoUsuarioEnum tipo;
}

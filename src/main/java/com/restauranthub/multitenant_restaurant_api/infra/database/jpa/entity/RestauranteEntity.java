package com.restauranthub.multitenant_restaurant_api.infra.database.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "restaurantes", schema = "restaurant_hub")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestauranteEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 150)
	private String nome;

	@Column(nullable = false, length = 255)
	private String endereco;

	@Column(nullable = false, length = 100)
	private String tipoCozinha;

	@Column(nullable = false, length = 100)
	private String horarioFuncionamento;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "dono_usuario_id", nullable = false)
	private UsuarioEntity dono;
}

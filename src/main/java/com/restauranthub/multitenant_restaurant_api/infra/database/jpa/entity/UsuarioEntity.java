package com.restauranthub.multitenant_restaurant_api.infra.database.jpa.entity;

import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios", schema = "restaurant_hub")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UsuarioEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 150)
	private String nome;

	@Column(nullable = false, unique = true, length = 255)
	private String email;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "usuarios_tipos_usuario",
			schema = "restaurant_hub",
			joinColumns = @JoinColumn(name = "usuario_id"),
			inverseJoinColumns = @JoinColumn(name = "tipo_usuario_id"))
	private Set<TipoUsuarioEntity> tiposUsuario = new LinkedHashSet<>();

	public UsuarioEntity(Long id, String nome, String email) {
		this(id, nome, email, new LinkedHashSet<>());
	}
}

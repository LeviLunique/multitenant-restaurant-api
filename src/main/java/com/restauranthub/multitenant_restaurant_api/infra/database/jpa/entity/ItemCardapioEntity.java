package com.restauranthub.multitenant_restaurant_api.infra.database.jpa.entity;

import java.math.BigDecimal;

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
@Table(name = "itens_cardapio", schema = "restaurant_hub")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemCardapioEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 150)
	private String nome;

	@Column(nullable = false, length = 500)
	private String descricao;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal preco;

	@Column(name = "apenas_consumo_no_local", nullable = false)
	private Boolean apenasConsumoNoLocal;

	@Column(name = "caminho_foto", nullable = false, length = 255)
	private String caminhoFoto;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "restaurante_id", nullable = false)
	private RestauranteEntity restaurante;
}

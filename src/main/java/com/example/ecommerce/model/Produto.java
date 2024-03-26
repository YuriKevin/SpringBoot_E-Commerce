package com.example.ecommerce.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Produto {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String titulo;
	private Double valor;
	private Long quantidade;
	private String categoria;
	private String nomeLoja;
	@ElementCollection
    private List<String> imagens;
	@ManyToOne(fetch = FetchType.LAZY)
    private Loja loja;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "produto_id")
    private List<DetalhesProduto> detalhes;
	private Long quantidadeVendida;
	private Long quantidadeAvaliacoes;
	private Long somaAvaliacoes;
	private Double avaliacao;
	private boolean disponivel;
}

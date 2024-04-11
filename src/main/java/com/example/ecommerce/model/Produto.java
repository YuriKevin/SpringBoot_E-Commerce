package com.example.ecommerce.model;

import java.util.List;
import java.util.ArrayList;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
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
	@DecimalMax(value = "9999999999.99", inclusive = true, message = "O valor deve ter no máximo duas casas decimais")
	private Double valor;
	@NotNull
	private Long quantidade;
	private String categoria;
	private String nomeLoja;
	@ElementCollection
	@Lob
    @Column(columnDefinition = "LONGTEXT")
    private List<String> imagens;
	@ManyToOne(fetch = FetchType.LAZY)
    private Loja loja;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "produto_id")
    private List<DetalhesProduto> detalhes;
	private Long quantidadeVendida;
	private Long quantidadeAvaliacoes;
	private Long somaAvaliacoes;
	@DecimalMax(value = "5.00", inclusive = true, message = "O valor deve ser no máximo 5.00")
	private Double avaliacao;
	private boolean disponivel;
}

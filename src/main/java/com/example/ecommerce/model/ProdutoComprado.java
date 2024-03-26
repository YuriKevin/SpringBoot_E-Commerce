package com.example.ecommerce.model;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ProdutoComprado {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@ManyToOne
	private Produto produto;
	private Double valor;
	private int quantidade;
	private boolean avaliado;
	private boolean cancelado;
	@ManyToOne(fetch = FetchType.LAZY)
	private Loja loja;
	@ManyToOne(fetch = FetchType.LAZY)
	private Usuario usuario;
}

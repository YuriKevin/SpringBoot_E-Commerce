package com.example.ecommerce.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProdutoCompradoDTO {
	private Long id;
	private Long produtoId;
	private String titulo;
	private String nomeLoja;
	private String nomeUsuario;
	private Double valor;
    private String imagem;
    private int quantidade;
	private boolean avaliado;
	private boolean cancelado;
}

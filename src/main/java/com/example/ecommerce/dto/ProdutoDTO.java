package com.example.ecommerce.dto;
import java.util.List;
import com.example.ecommerce.model.DetalhesProduto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProdutoDTO {
	private Long id;
	private Long lojaId;
	private String titulo;
	private Double valor;
	private String categoria;
	private String nomeLoja;
    private List<String> imagens;
    private List<DetalhesProduto> detalhes;
    private Long quantidade;
	private Long quantidadeVendida;
	private Double avaliacao;
	private boolean disponivel;
}

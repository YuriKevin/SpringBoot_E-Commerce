package com.example.ecommerce.requests;

import java.util.List;
import com.example.ecommerce.model.DetalhesProduto;
import lombok.Data;

@Data
public class ProdutoPostRequestBody {
	private String titulo;
	private Double valor;
	private Long quantidade;
	private String categoria;
    private List<String> imagens;
    private Long lojaId;
    private List<DetalhesProduto> detalhes;
	private boolean disponivel;
}

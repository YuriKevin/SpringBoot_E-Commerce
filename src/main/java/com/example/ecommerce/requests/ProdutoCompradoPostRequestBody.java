package com.example.ecommerce.requests;
import lombok.Data;

@Data
public class ProdutoCompradoPostRequestBody {
	private Long produtoId;
	private int quantidade;
}

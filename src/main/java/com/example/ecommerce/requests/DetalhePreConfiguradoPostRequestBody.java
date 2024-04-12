package com.example.ecommerce.requests;
import java.util.List;
import lombok.Data;

@Data
public class DetalhePreConfiguradoPostRequestBody {
	private String titulo;
    private List<DetalhesProdutoPostRequestBody> detalhes;
}

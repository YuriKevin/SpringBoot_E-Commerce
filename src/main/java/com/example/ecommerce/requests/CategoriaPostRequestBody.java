package com.example.ecommerce.requests;
import java.util.List;
import lombok.Data;

@Data
public class CategoriaPostRequestBody {
	private String titulo;
    private List<Long> idsProdutos;
}

package com.example.ecommerce.requests;
import lombok.Data;

@Data
public class UsuarioPostRequestBody {
	private String nome;
	private String email;
	private String senha;
}

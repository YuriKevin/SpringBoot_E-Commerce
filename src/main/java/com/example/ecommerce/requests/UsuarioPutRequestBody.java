package com.example.ecommerce.requests;

import lombok.Data;

@Data
public class UsuarioPutRequestBody {
	private Long id;
	private String nome;
	private String senha;
}

package com.example.ecommerce.requests;
import lombok.Data;

@Data
public class LojaPutRequestBody {
	private Long id;
	private String nome;
    private String senha;
    private String logo;
}

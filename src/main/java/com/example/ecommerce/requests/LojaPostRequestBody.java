package com.example.ecommerce.requests;
import lombok.Data;

@Data
public class LojaPostRequestBody {
    private Long codigoLogin;
    private String nome;
    private String senha;
    private String logo;
}

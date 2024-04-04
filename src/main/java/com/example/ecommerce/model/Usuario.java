package com.example.ecommerce.model;
import javax.validation.constraints.DecimalMax;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Usuario {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String nome;
	@Column(unique = true)
	private String email;
	private String senha;
	@DecimalMax(value = "9999999999.99", inclusive = true, message = "O valor deve ter no máximo duas casas decimais")
	private Double credito;
}

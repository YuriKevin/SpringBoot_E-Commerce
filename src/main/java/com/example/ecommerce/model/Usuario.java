package com.example.ecommerce.model;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
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
	@NotNull
	private String nome;
	@Column(unique = true)
	@NotNull
	private String email;
	@NotNull
	private String senha;
	@Digits(integer=10, fraction=2)
	private Double credito;
}

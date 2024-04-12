package com.example.ecommerce.model;
import java.util.List;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Loja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @NotNull
    private Long codigoLogin;
    @NotNull
    private String nome;
    @NotNull
    private String senha;
    @DecimalMax(value = "9999999999.99", inclusive = true, message = "O valor deve ter no máximo duas casas decimais")
    private Double credito;
    private Long quantidadeVendida;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    @NotNull
    private String logo;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "loja_id")
    private List<DetalhesPreConfigurados> detalhesPreConfigurados;
    private Long quantidadeAvaliacoes;
	private Long somaAvaliacoes;
	@DecimalMax(value = "5.00", inclusive = true, message = "O valor deve ser no máximo 5.00")
	private Double avaliacao;
}

package com.example.ecommerce.repository;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.ecommerce.model.Loja;

public interface LojaRepository extends JpaRepository<Loja, Long>{
	Loja findByCodigoLogin(Long codigoLogin);
	List<Loja> findByNomeOrderByQuantidadeVendidaDesc(String nome, Pageable page);
	
	@Query("SELECT l FROM Loja l ORDER BY l.quantidadeVendida DESC")
    List<Loja> findTop20ByOrderByQuantidadeVendidaDesc();
}

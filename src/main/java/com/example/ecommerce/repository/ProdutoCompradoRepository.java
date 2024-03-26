package com.example.ecommerce.repository;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.ecommerce.model.Loja;
import com.example.ecommerce.model.ProdutoComprado;
import com.example.ecommerce.model.Usuario;

public interface ProdutoCompradoRepository extends JpaRepository<ProdutoComprado, Long>{
	
	@Query("SELECT p FROM ProdutoComprado p WHERE p.loja = :loja")
    List<ProdutoComprado> findByLoja(@Param("loja") Loja loja, Pageable page);
	
	 @Query("SELECT p FROM ProdutoComprado p WHERE p.usuario = :usuario")
	 List<ProdutoComprado> findByUsuario(@Param("usuario") Usuario usuario, Pageable page);
}
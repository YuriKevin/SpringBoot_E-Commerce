package com.example.ecommerce.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.ecommerce.model.Loja;
import com.example.ecommerce.model.Produto;
import jakarta.transaction.Transactional;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	@Transactional
    void deleteByLoja(Loja loja);
	List<Produto> findByLojaOrderByQuantidadeVendidaDesc(Loja loja, Pageable page);
	List<Produto> findByTituloContainingIgnoreCaseOrderByQuantidadeVendidaDesc(String titulo, Pageable page);
	List<Produto> findByCategoriaContainingIgnoreCaseOrderByQuantidadeVendidaDesc(String categoria, Pageable page);
	
	@Query("SELECT p FROM Produto p ORDER BY p.id DESC")
	List<Produto> findLast30ByIdDesc();
	
	@Query("SELECT p FROM Produto p ORDER BY p.quantidadeVendida DESC")
    List<Produto> findTop10ByOrderByQuantidadeVendidaDesc();
	
	@Query("SELECT p FROM Produto p WHERE p.loja = :loja ORDER BY p.quantidadeVendida DESC")
	List<Produto> findTop10ByLojaOrderByQuantidadeVendidaDesc(@Param("loja") Loja loja);
	
	@Transactional
    @Modifying
    @Query("UPDATE Produto p SET p.disponivel = false WHERE p.loja = :loja")
    void setDisponivelFalseByLoja(@Param("loja") Loja loja);
	
	@Transactional
    @Modifying
    @Query("UPDATE Produto p SET p.disponivel = true WHERE p.loja = :loja")
    void setDisponivelTrueByLoja(@Param("loja") Loja loja);
	
	 @Query("SELECT p FROM Produto p WHERE LOWER(p.titulo) LIKE LOWER(concat('%', :titulo, '%')) ORDER BY RAND() LIMIT 4")
	 List<Produto> findRandomByTitulo(@Param("titulo") String titulo);
	 
	 List<Produto> findByLojaAndTituloContainingIgnoreCase(Loja loja, String titulo, Pageable page);

}

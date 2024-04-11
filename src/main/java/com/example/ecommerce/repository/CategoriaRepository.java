package com.example.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce.model.Categoria;
import com.example.ecommerce.model.Loja;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
	List<Categoria> findByLoja(Loja loja);
}

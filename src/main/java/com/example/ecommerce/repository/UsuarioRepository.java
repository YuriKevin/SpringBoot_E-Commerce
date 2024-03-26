package com.example.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	 Usuario findByEmail(String email);
}

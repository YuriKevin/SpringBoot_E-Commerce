package com.example.ecommerce.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.ecommerce.model.Usuario;
import com.example.ecommerce.requests.UsuarioPostRequestBody;
import com.example.ecommerce.requests.UsuarioPutRequestBody;
import com.example.ecommerce.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@Component
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController {
	private final UsuarioService usuarioService;
	
	@GetMapping(path = "/{id}")
    public ResponseEntity<Usuario> encontrarPorId(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.encontrarPorIdOuExcecao(id));
    }
	
	@GetMapping(path = "/login")
    public ResponseEntity<Usuario> login(@RequestParam String email, @RequestParam String senha){
        return ResponseEntity.ok(usuarioService.login(email, senha));
    }

	@PostMapping
    public ResponseEntity<Usuario> cadastrar(@RequestBody @Valid UsuarioPostRequestBody usuario){
        return new ResponseEntity<>(usuarioService.cadastrar(usuario), HttpStatus.CREATED);
    }
	
	@PutMapping
    public ResponseEntity<Void> atualizarDados(@RequestBody UsuarioPutRequestBody usuario){
		usuarioService.atualizarDados(usuario);
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
	
	@PutMapping(path = "/adicionarCredito")
    public ResponseEntity<Void> adicionarCredito(@RequestParam Long id, @RequestParam Double credito){
		usuarioService.adicionarCredito(id, credito);
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
	
	@PutMapping(path = "/removerCredito")
    public ResponseEntity<Void> removerCredito(@RequestParam Long id, @RequestParam Double credito){
		usuarioService.removerCredito(id, credito);
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
	
	
	
}

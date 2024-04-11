package com.example.ecommerce.controller;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.dto.CategoriaDTO;
import com.example.ecommerce.requests.CategoriaPostRequestBody;
import com.example.ecommerce.service.CategoriaService;
import lombok.RequiredArgsConstructor;

@Component
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("/categoria")
public class CategoriaController {
	private final CategoriaService categoriaService;
	
	@PostMapping(path = "/{lojaId}")
    public ResponseEntity<Void> adicionarCategoria(@PathVariable Long lojaId, @RequestBody @Valid CategoriaPostRequestBody categoria){
		categoriaService.adicionarCategoria(lojaId, categoria);
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
	
	@DeleteMapping(path = "/{LojaId}/{categoriaId}")
    public ResponseEntity<Void> removerCategoria(@PathVariable Long LojaId, @PathVariable Long categoriaId){
		categoriaService.removerCategoria(LojaId, categoriaId);
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
	
	@GetMapping(path = "/categorias/{lojaId}")
    public ResponseEntity<List<CategoriaDTO>> listarProdutosCategorizadosDeUmaLoja(@PathVariable Long lojaId){
        return ResponseEntity.ok(categoriaService.listarProdutosCategorizadosDeUmaLoja(lojaId));
    }
	
}

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.dto.LojaDTO;
import com.example.ecommerce.model.Categoria;
import com.example.ecommerce.model.DetalhesPreConfigurados;
import com.example.ecommerce.model.Loja;
import com.example.ecommerce.requests.CategoriaPostRequestBody;
import com.example.ecommerce.requests.LojaPostRequestBody;
import com.example.ecommerce.requests.LojaPutRequestBody;
import com.example.ecommerce.service.LojaService;
import lombok.RequiredArgsConstructor;

@Component
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("/loja")
public class LojaController {
	private final LojaService lojaService;
	
	@GetMapping(path = "/{id}")
    public ResponseEntity<LojaDTO> encontrarPorIdDTO(@PathVariable Long id){
        return ResponseEntity.ok(lojaService.encontrarPorIdDTO(id));
    }
	
	@GetMapping(path = "/nome/{nome}")
    public ResponseEntity<List<LojaDTO>> encontrarPorNome(@PathVariable String nome, @RequestParam int pagina){
        return ResponseEntity.ok(lojaService.encontrarPorNome(nome, pagina));
    }
	
	
	@GetMapping(path = "/populares")
    public ResponseEntity<List<LojaDTO>> listarLojasMaisPopulares(){
        return ResponseEntity.ok(lojaService.listarLojasMaisPopulares());
    }
	
	@GetMapping(path = "/listar/{pagina}")
    public ResponseEntity<List<LojaDTO>> listarLojas(@PathVariable int pagina){
        return ResponseEntity.ok(lojaService.listarLojas(pagina));
    }
	
	@GetMapping(path = "/login")
    public ResponseEntity<Loja> login(@RequestParam Long codigoLogin, @RequestParam String senha){
        return ResponseEntity.ok(lojaService.login(codigoLogin, senha));
    }
	
	@PostMapping
    public ResponseEntity<Loja> cadastrar(@RequestBody @Valid LojaPostRequestBody loja){
        return new ResponseEntity<>(lojaService.cadastrar(loja), HttpStatus.CREATED);
    }
	
	@PutMapping
    public ResponseEntity<Void> atualizarDados(@RequestBody @Valid LojaPutRequestBody loja){
		lojaService.atualizarDados(loja);
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
	
	@PutMapping(path = "/adicionarDetalhe")
    public ResponseEntity<Void> adicionarDetalhePreConfigurado(@RequestParam Long id, @RequestBody @Valid DetalhesPreConfigurados detalhe){
		lojaService.adicionarDetalhePreConfigurado(id, detalhe);
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
	
	@PutMapping(path = "/removerDetalhe")
    public ResponseEntity<Void> removerDetalhePreConfigurado(@RequestParam Long id, @RequestBody @Valid DetalhesPreConfigurados detalhe){
		lojaService.removerDetalhePreConfigurado(id, detalhe);
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
	
	@PutMapping(path = "/atualizarSenha")
    public ResponseEntity<Void> atualizarSenha(@RequestParam Long id, @RequestParam String senha, @RequestParam String senhaAntiga){
		lojaService.atualizarSenha(id, senha, senhaAntiga);
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

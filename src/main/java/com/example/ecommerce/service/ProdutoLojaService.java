package com.example.ecommerce.service;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.ecommerce.model.Loja;
import com.example.ecommerce.model.Produto;
import com.example.ecommerce.repository.ProdutoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdutoLojaService {
	private final ProdutoRepository produtoRepository;
	
	@Transactional
	public void trocarNomeLojaProdutos(Loja loja, String novoNome){
		List<Produto> produtos = produtoRepository.findByLoja(loja);
		for(Produto produto : produtos) {
			produto.setNomeLoja(novoNome);
			produtoRepository.save(produto);
		}
	}
	public Produto encontrarProdutoPorId(Long id) {
		return produtoRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto Não encontrado"));
	}
}

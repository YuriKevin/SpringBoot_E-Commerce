package com.example.ecommerce.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ecommerce.dto.CategoriaDTO;
import com.example.ecommerce.dto.ProdutoDTO;
import com.example.ecommerce.model.Categoria;
import com.example.ecommerce.model.Loja;
import com.example.ecommerce.model.Produto;
import com.example.ecommerce.repository.CategoriaRepository;
import com.example.ecommerce.requests.CategoriaPostRequestBody;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaService {
	private final CategoriaRepository categoriaRepository;
	private final LojaService lojaService;
	private final ProdutoService produtoService;
	
	@Transactional
	public void adicionarCategoria(Long lojaId, CategoriaPostRequestBody categoriaPost) {
		Loja loja = lojaService.encontrarPorIdOuExcecao(lojaId);
		List<Produto> produtos = new ArrayList<>();
		for(Long produtoId : categoriaPost.getIdsProdutos()) {
			Produto produtoSalvo = produtoService.encontrarPorIdOuExcecao(produtoId);
			produtos.add(produtoSalvo);
		}
		categoriaRepository.save(Categoria.builder()
				.produtos(produtos)
				.titulo(categoriaPost.getTitulo())
				.loja(loja)
				.build());
	}
	
	@Transactional
	public void removerCategoria(Long lojaId, Long categoriaId) {
		categoriaRepository.deleteById(categoriaId);
	}
	
	@Transactional
	public List<CategoriaDTO> listarProdutosCategorizadosDeUmaLoja(Long id) {
		Loja loja = lojaService.encontrarPorIdOuExcecao(id);
		List<CategoriaDTO> categorias = transformarCategoriasEmDTO(categoriaRepository.findByLoja(loja));
		return categorias;
	}
	
	public List<CategoriaDTO> transformarCategoriasEmDTO(List<Categoria> categorias){
		List<CategoriaDTO> categoriasDTO = new ArrayList<>();
		for(Categoria categoriaSalva : categorias) {
			List<ProdutoDTO> produtosDTO = produtoService.transformarProdutosEmDTO(categoriaSalva.getProdutos());
			CategoriaDTO categoriaDTO = CategoriaDTO.builder()
					.id(categoriaSalva.getId())
					.titulo(categoriaSalva.getTitulo())
					.produtos(produtosDTO)
					.build();
					categoriasDTO.add(categoriaDTO);
		}
		return categoriasDTO;
	}
}

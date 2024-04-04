package com.example.ecommerce.service;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import com.example.ecommerce.repository.ProdutoCompradoRepository;
import com.example.ecommerce.requests.ProdutoCompradoPostRequestBody;

import jakarta.transaction.Transactional;

import com.example.ecommerce.dto.ProdutoCompradoDTO;
import com.example.ecommerce.model.ProdutoComprado;
import com.example.ecommerce.model.Usuario;
import com.example.ecommerce.model.Loja;
import com.example.ecommerce.model.Produto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdutoCompradoService {
	private final ProdutoCompradoRepository produtoCompradoRepository;
	private final LojaService lojaService;
	private final UsuarioService usuarioService;
	private final ProdutoService produtoService;
	
	@Transactional
	public ProdutoComprado encontrarPorIdOuExcecao(Long id){
		return produtoCompradoRepository.findById(id)
        		.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto Não encontrado"));
	}
	
	@Transactional
	public List<ProdutoCompradoDTO> novaCompra(Long usuarioId, List<ProdutoCompradoPostRequestBody> produtos){
		List<ProdutoComprado> produtosComprados = new ArrayList<>();
		Usuario usuario = usuarioService.encontrarPorIdOuExcecao(usuarioId);
		for(ProdutoCompradoPostRequestBody produto : produtos) {
			Produto produtoSalvo = produtoService.encontrarPorIdOuExcecao(produto.getProdutoId());
			if(produtoSalvo.getQuantidade()<produto.getQuantidade()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto com estoque insuficiente. Quantidade disponível: "+produtoSalvo.getQuantidade());
			}
			Loja loja = produtoSalvo.getLoja();
			ProdutoComprado produtoComprado = produtoCompradoRepository.save(ProdutoComprado.builder()
					.produto(produtoSalvo)
					.valor(produtoSalvo.getValor())
					.quantidade(produto.getQuantidade())
					.avaliado(false)
					.cancelado(false)
					.usuario(usuario)
					.loja(loja)
					.build());
			produtosComprados.add(produtoComprado);
			produtoService.produtoVendido(produtoSalvo, produto.getQuantidade());
			produtoService.adicionarQuantidadeVendida(produtoComprado);
		}
		Double valorTotalCompra = calcularValorCompra(produtosComprados);
		usuarioService.removerCredito(usuarioId, valorTotalCompra);
		List<ProdutoCompradoDTO> produtosCompradosDTO = transformarArrayEmDTO(produtosComprados);
		
		return produtosCompradosDTO;
	}
	
	@Transactional
	public List<ProdutoCompradoDTO> listarProdutosVendidosPorUmaLoja(Long lojaId, int pagina){
		Loja loja = lojaService.encontrarPorIdOuExcecao(lojaId);
		List<ProdutoCompradoDTO> produtosCompradosDTO = transformarArrayEmDTO(produtoCompradoRepository.findByLoja(loja, PageRequest.of(pagina, 18)));
		return produtosCompradosDTO;
	}
	
	@Transactional
	public List<ProdutoCompradoDTO> listarProdutosCompradosPorUmUsuario(Long usuarioId, int pagina){
		Usuario usuario = usuarioService.encontrarPorIdOuExcecao(usuarioId);
		List<ProdutoCompradoDTO> produtosCompradosDTO = transformarArrayEmDTO(produtoCompradoRepository.findByUsuario(usuario, PageRequest.of(pagina, 19)));
		return produtosCompradosDTO;
	}
	
	public List<ProdutoCompradoDTO> transformarArrayEmDTO(List<ProdutoComprado> produtos){
		List<ProdutoCompradoDTO> produtosCompradosDTO = new ArrayList<>();
		for(ProdutoComprado produto : produtos) {
			ProdutoCompradoDTO produtoDTO = transformarEmDTO(produto);
			produtosCompradosDTO.add(produtoDTO);
		}
		return produtosCompradosDTO;
	}
	
	@Transactional
	public ProdutoCompradoDTO transformarEmDTO(ProdutoComprado produto){
		ProdutoCompradoDTO produtoCompradoDTO = ProdutoCompradoDTO.builder()
				.id(produto.getId())
				.produtoId(produto.getProduto().getId())
				.titulo(produto.getProduto().getTitulo())
				.nomeLoja(produto.getProduto().getNomeLoja())
				.nomeUsuario(produto.getUsuario().getNome())
				.valor(produto.getValor())
				.imagem(produto.getProduto().getImagens().get(0))
				.quantidade(produto.getQuantidade())
				.avaliado(produto.isAvaliado())
				.cancelado(produto.isCancelado())
				.build();
		 return produtoCompradoDTO;
	}
	
	@Transactional
	public void avaliarProdutoComprado(Long produtoCompradoId, int avaliacao) {
		if(avaliacao!=0 && avaliacao!=1 && avaliacao!=2 && avaliacao!=3 && avaliacao!=4 && avaliacao!=5) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Escolha um número inteiro de 0 a 5 para avaliar.");
		}
		ProdutoComprado produto = encontrarPorIdOuExcecao(produtoCompradoId);
		if(produto.isAvaliado()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este produto ja foi avaliado.");
		}
		produto.setAvaliado(true);
		produtoCompradoRepository.save(produto);
		produtoService.avaliarProduto(produto.getProduto(), avaliacao);
	}
	
	@Transactional
	public void cancelarCompra(Long produtoCompradoId) {
		ProdutoComprado produto = encontrarPorIdOuExcecao(produtoCompradoId);
		if(produto.isCancelado()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este produto ja foi cancelado.");
		}
		produto.setCancelado(true);
		reembolsarCliente(produto);
	}
	
	@Transactional
	public void reembolsarCliente(ProdutoComprado produto) {
		usuarioService.adicionarCredito(produto.getUsuario().getId(), produto.getValor());
		lojaService.removerCredito(produto.getLoja().getId(), produto.getValor());
	}
	
	public Double calcularValorCompra(List<ProdutoComprado> produtos) {
		Double valorCompra = 0D;
		for(ProdutoComprado produto : produtos) {
			valorCompra += produto.getValor() * produto.getQuantidade();
		}
		return valorCompra;
	}
	
}

package com.example.ecommerce.service;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.example.ecommerce.dto.CategoriaDTO;
import com.example.ecommerce.dto.ProdutoDTO;
import com.example.ecommerce.model.Produto;
import com.example.ecommerce.repository.ProdutoRepository;
import com.example.ecommerce.requests.ProdutoPostRequestBody;
import com.example.ecommerce.requests.ProdutoPutRequestBody;
import jakarta.transaction.Transactional;
import com.example.ecommerce.model.Categoria;
import com.example.ecommerce.model.Loja;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdutoService {
	private final ProdutoRepository produtoRepository;
	private final LojaService lojaService;
	
	
	@Transactional
	public Produto encontrarPorIdOuExcecao(Long id){
		return produtoRepository.findById(id)
        		.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto Não encontrado"));
	}
	
	@Transactional
	public ProdutoDTO encontrarPorIdDTO(Long id){
		Produto produto = encontrarPorIdOuExcecao(id);
		 ProdutoDTO produtoDTO = ProdutoDTO.builder()
				.id(produto.getId())
				.lojaId(produto.getLoja().getId())
				.titulo(produto.getTitulo())
				.valor(produto.getValor())
				.imagens(produto.getImagens())
				.nomeLoja(produto.getNomeLoja())
				.detalhes(produto.getDetalhes())
				.quantidade(produto.getQuantidade())
				.quantidadeVendida(produto.getQuantidadeVendida())
				.avaliacao(produto.getAvaliacao())
				.categoria(produto.getCategoria())
				.disponivel(produto.isDisponivel())
				.build();
		 return produtoDTO;
	}
	
	@Transactional
	public List<Produto> listarPorTitulo(String titulo, int pagina){
		return produtoRepository.findByTituloContainingIgnoreCaseOrderByQuantidadeVendidaDesc(titulo, PageRequest.of(pagina, 18));
	}
	
	@Transactional
	public List<ProdutoDTO> listarPorTituloDTO(String titulo, int pagina){
		List<Produto> produtosSalvos = listarPorTitulo(titulo, pagina);
		verificarListaVaziaExcecao(produtosSalvos);
		List<ProdutoDTO> produtosDTO = transformarProdutosEmDTO(produtosSalvos);
		return produtosDTO;
	}
	
	@Transactional
	public List<Produto> listarPorCategoria(String categoria, int pagina){
		return produtoRepository.findByCategoriaContainingIgnoreCaseOrderByQuantidadeVendidaDesc(categoria, PageRequest.of(pagina, 18));		
	}
	
	@Transactional
	public List<ProdutoDTO> listarPorCategoriaDTO(String categoria, int pagina){
		List<Produto> produtosSalvos = listarPorCategoria(categoria, pagina);
		verificarListaVaziaExcecao(produtosSalvos);
		List<ProdutoDTO> produtosDTO = transformarProdutosEmDTO(produtosSalvos);
		return produtosDTO;
	}
	
	@Transactional
	public List<Produto> listarProdutosDeUmaloja(Long lojaId, int pagina){
		Loja loja = lojaService.encontrarPorIdOuExcecao(lojaId);
		return produtoRepository.findByLojaOrderByQuantidadeVendidaDesc(loja, PageRequest.of(pagina, 18));		
	}
	
	@Transactional
	public List<ProdutoDTO> listarProdutosDeUmalojaPorTitulo(Long lojaId, String titulo, int pagina ){
		Loja loja = lojaService.encontrarPorIdOuExcecao(lojaId);
		return transformarProdutosEmDTO(produtoRepository.findByLojaAndTituloContainingIgnoreCase(loja, titulo, PageRequest.of(pagina, 18)));		
	}
	
	@Transactional
	public List<ProdutoDTO> listarProdutosDeUmalojaDTO(Long loja, int pagina){
		List<Produto> produtosSalvos = listarProdutosDeUmaloja(loja, pagina);
		verificarListaVaziaExcecao(produtosSalvos);
		List<ProdutoDTO> produtosDTO = transformarProdutosEmDTO(produtosSalvos);
		return produtosDTO;
	}
	
	@Transactional
	public List<ProdutoDTO> listarProdutosMaisVendidos(){
		List<Produto> produtosSalvos = produtoRepository.findTop10ByOrderByQuantidadeVendidaDesc();
		verificarListaVaziaExcecao(produtosSalvos);
		List<ProdutoDTO> produtosDTO = transformarProdutosEmDTO(produtosSalvos);
		 return produtosDTO;
	}
	
	@Transactional
	public List<ProdutoDTO> listarProdutosMaisVendidosDeUmaLoja(Long lojaId){
		Loja loja = lojaService.encontrarPorIdOuExcecao(lojaId);
		List<Produto> produtosSalvos = produtoRepository.findTop10ByLojaOrderByQuantidadeVendidaDesc(loja);
		verificarListaVaziaExcecao(produtosSalvos);
		List<ProdutoDTO> produtosDTO = transformarProdutosEmDTO(produtosSalvos);
		return produtosDTO;
	}
	
	public List<ProdutoDTO> transformarProdutosEmDTO(List<Produto> produtos){
		List<ProdutoDTO> produtosDTO = new ArrayList<>();
		for(Produto produtoSalvo : produtos) {
			List<String> imagemPrincipal = new ArrayList<>();
			imagemPrincipal.add(produtoSalvo.getImagens().get(0));
			ProdutoDTO produtoDTO = ProdutoDTO.builder()
					.id(produtoSalvo.getId())
					.titulo(produtoSalvo.getTitulo())
					.valor(produtoSalvo.getValor())
					.imagens(imagemPrincipal)
					.detalhes(produtoSalvo.getDetalhes())
					.quantidade(produtoSalvo.getQuantidade())
					.quantidadeVendida(produtoSalvo.getQuantidadeVendida())
					.avaliacao(produtoSalvo.getAvaliacao())
					.disponivel(produtoSalvo.isDisponivel())
					.build();
			produtosDTO.add(produtoDTO);
		}
		return produtosDTO;
	}
	
	public ProdutoDTO transformarUmProdutoEmDTO(Produto produto){
			ProdutoDTO produtoDTO = ProdutoDTO.builder()
					.id(produto.getId())
					.titulo(produto.getTitulo())
					.valor(produto.getValor())
					.imagens(produto.getImagens())
					.detalhes(produto.getDetalhes())
					.quantidade(produto.getQuantidade())
					.quantidadeVendida(produto.getQuantidadeVendida())
					.avaliacao(produto.getAvaliacao())
					.disponivel(produto.isDisponivel())
					.build();
		
		return produtoDTO;
	}
	
	@Transactional
	public ProdutoDTO novoProduto(ProdutoPostRequestBody produto) {
		if(produto.getImagens().size()>6) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "é permitido no máximo 6 imagens por produto.");
		}
		Loja loja = lojaService.encontrarPorIdOuExcecao(produto.getLojaId());
		Produto novoProduto = produtoRepository.save(Produto.builder()
				.titulo(produto.getTitulo())
				.valor(produto.getValor())
				.categoria(produto.getCategoria())
				.nomeLoja(loja.getNome())
				.imagens(produto.getImagens())
				.loja(loja)
				.detalhes(produto.getDetalhes())
				.quantidade(produto.getQuantidade())
				.quantidadeVendida(0L)
				.quantidadeAvaliacoes(0L)
				.somaAvaliacoes(0L)
				.avaliacao(null)
				.build());
		
		return transformarUmProdutoEmDTO(novoProduto);
	}
	
	@Transactional
	public void atualizarProduto(ProdutoPutRequestBody produto) {
		Produto produtoSalvo = encontrarPorIdOuExcecao(produto.getId());
		produtoSalvo.setTitulo(produto.getTitulo());
		produtoSalvo.setValor(produto.getValor());
		produtoSalvo.setCategoria(produto.getCategoria());
		if(produto.getImagens().size()>6) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "é permitido no máximo 6 imagens por produto.");
		}
		produtoSalvo.setImagens(produto.getImagens());
		produtoSalvo.setDetalhes(produto.getDetalhes());
		produtoRepository.save(produtoSalvo);
	}
	
	@Transactional
	public void deletarProduto(Long id){
		produtoRepository.delete(encontrarPorIdOuExcecao(id));
	}
	
	@Transactional
	public void avaliarProduto(Produto produto, int avaliacao) {
		produto.setQuantidadeAvaliacoes(produto.getQuantidadeAvaliacoes()+1);
		produto.setSomaAvaliacoes(produto.getSomaAvaliacoes()+avaliacao);
		produto.setAvaliacao(calcularAvaliacao(produto.getSomaAvaliacoes(), produto.getQuantidadeAvaliacoes()));
		produtoRepository.save(produto);
	}
	
	public Double calcularAvaliacao(Long soma, Long quantidade) {
		return (double) (soma/quantidade);
	}
	
	@Transactional
	public void desativarProdutosDeUmaLoja(Long id){
		Loja lojaSalva = lojaService.encontrarPorIdOuExcecao(id);
		produtoRepository.setDisponivelFalseByLoja(lojaSalva);
	}
	
	@Transactional
	public void ativarProdutosDeUmaLoja(Long id){
		Loja lojaSalva = lojaService.encontrarPorIdOuExcecao(id);
		produtoRepository.setDisponivelTrueByLoja(lojaSalva);
	}
	
	@Transactional
	public void produtoVendido(Produto produto, int quantidade){
		produto.setQuantidade(produto.getQuantidade() - quantidade);
		produto.setQuantidadeVendida(produto.getQuantidadeVendida() + quantidade);
		produtoRepository.save(produto);
		lojaService.adicionarCredito(produto.getLoja(), produto.getValor() * quantidade);
		lojaService.adicionarQuantidadeVendida(produto.getLoja(), quantidade);
	}
	
	public void verificarListaVaziaExcecao(List<Produto> produtos) {
		if(produtos.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "0 resultados encontrados para a pesquisa.");
		}
	}
	
	@Transactional
	public List<CategoriaDTO> listarProdutosCategorizadosDeUmaLoja(Long id) {
		Loja loja = lojaService.encontrarPorIdOuExcecao(id);
		List<CategoriaDTO> categorias = transformarCategoriasEmDTO(loja.getCategorias());
		return categorias;
	}
	
	public List<CategoriaDTO> transformarCategoriasEmDTO(List<Categoria> categorias){
		List<CategoriaDTO> categoriasDTO = new ArrayList<>();
		for(Categoria categoriaSalva : categorias) {
			List<ProdutoDTO> produtosDTO = transformarProdutosEmDTO(categoriaSalva.getProdutos());
			CategoriaDTO categoriaDTO = CategoriaDTO.builder()
					.id(categoriaSalva.getId())
					.titulo(categoriaSalva.getTitulo())
					.produtos(produtosDTO)
					.build();
					categoriasDTO.add(categoriaDTO);
		}
		return categoriasDTO;
	}
	
	@Transactional
	public List<ProdutoDTO> produtosEmDestaque(){
		List<ProdutoDTO> produtosDTO = new ArrayList<>();
		 ProdutoDTO produto1 = transformarUmProdutoEmDTO(encontrarPorIdOuExcecao(1L));
		 produtosDTO.add(produto1);
		 ProdutoDTO produto3 = transformarUmProdutoEmDTO(encontrarPorIdOuExcecao(3L));
		 produtosDTO.add(produto3);
		 ProdutoDTO produto4 = transformarUmProdutoEmDTO(encontrarPorIdOuExcecao(4L));
		 produtosDTO.add(produto4);
		 return produtosDTO;
	}
	
	@Transactional
	public List<ProdutoDTO> produtosHistoricoNavegacao(List<String> palavrasPesquisadas){
		List<Produto> produtosSalvos = new ArrayList<>();
		for(String palavra : palavrasPesquisadas) {
			 produtosSalvos.addAll(produtoRepository.findRandomByTitulo(palavra));
		}
		Collections.shuffle(produtosSalvos);
		List<ProdutoDTO> produtosDTO = transformarProdutosEmDTO(produtosSalvos);
		return produtosDTO;
	}
	
	@Transactional
	public List<ProdutoDTO> produtosMaisRecentes(){
		List<ProdutoDTO> produtosDTO = transformarProdutosEmDTO(produtoRepository.findLast30ByIdDesc());
		 return produtosDTO;
	}
	
	
}

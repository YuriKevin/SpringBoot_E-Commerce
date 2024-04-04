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
import com.example.ecommerce.dto.CategoriaDTO;
import com.example.ecommerce.dto.ProdutoDTO;
import com.example.ecommerce.model.Produto;
import com.example.ecommerce.requests.ProdutoPostRequestBody;
import com.example.ecommerce.requests.ProdutoPutRequestBody;
import com.example.ecommerce.service.ProdutoService;
import lombok.RequiredArgsConstructor;


@Component
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("/produto")
public class ProdutoController {
	private final ProdutoService produtoService;
	
	@GetMapping(path = "/teste/{id}")
    public ResponseEntity<Produto> encontrarPorId(@PathVariable Long id){
        return ResponseEntity.ok(produtoService.encontrarPorIdOuExcecao(id));
    }
	@GetMapping(path = "/{id}")
    public ResponseEntity<ProdutoDTO> encontrarPorIdDTO(@PathVariable Long id){
        return ResponseEntity.ok(produtoService.encontrarPorIdDTO(id));
    }
	
	@GetMapping(path = "/titulo/{titulo}")
    public ResponseEntity<List<ProdutoDTO>> listarPorTituloDTO(@PathVariable String titulo, @RequestParam String pagina){
        return ResponseEntity.ok(produtoService.listarPorTituloDTO(titulo, Integer.parseInt(pagina)));
    }
	
	@GetMapping(path = "/categoria/{categoria}")
    public ResponseEntity<List<ProdutoDTO>> listarPorCategoriaDTO(@PathVariable String categoria, @RequestParam String pagina){
        return ResponseEntity.ok(produtoService.listarPorCategoriaDTO(categoria, Integer.parseInt(pagina)));
    }
	
	@GetMapping(path = "/loja/populares/{lojaId}")
    public ResponseEntity<List<ProdutoDTO>> listarProdutosDeUmalojaDTO(@PathVariable Long lojaId, @RequestParam String pagina){
        return ResponseEntity.ok(produtoService.listarProdutosDeUmalojaDTO(lojaId, Integer.parseInt(pagina)));
    }
	
	@GetMapping(path = "/loja/{lojaId}")
    public ResponseEntity<List<ProdutoDTO>> listarProdutosMaisVendidosDeUmaLoja(@PathVariable Long lojaId, @RequestParam String pagina){
        return ResponseEntity.ok(produtoService.listarProdutosMaisVendidosDeUmaLoja(lojaId));
    }
	
	@GetMapping(path = "/categorias/{id}")
    public ResponseEntity<List<CategoriaDTO>> listarProdutosCategorizadosDeUmaLoja(@PathVariable Long id){
        return ResponseEntity.ok(produtoService.listarProdutosCategorizadosDeUmaLoja(id));
    }
	
	@GetMapping(path = "/top")
    public ResponseEntity<List<ProdutoDTO>> listarProdutosMaisVendidos(){
        return ResponseEntity.ok(produtoService.listarProdutosMaisVendidos());
    }
	
	@GetMapping(path = "/recentes")
    public ResponseEntity<List<ProdutoDTO>> listarMaisRecentes(){
        return ResponseEntity.ok(produtoService.produtosMaisRecentes());
    }
	
	@GetMapping(path = "/destaques")
    public ResponseEntity<List<ProdutoDTO>> produtosEmDestaque(){
        return ResponseEntity.ok(produtoService.produtosEmDestaque());
    }
	
	@GetMapping(path = "/historico")
    public ResponseEntity<List<ProdutoDTO>> produtosHistoricoNavegacao(@RequestParam List<String> palavrasPesquisadas){
        return ResponseEntity.ok(produtoService.produtosHistoricoNavegacao(palavrasPesquisadas));
    }
	
	@PostMapping
    public ResponseEntity<ProdutoDTO> novoProduto(@RequestBody @Valid ProdutoPostRequestBody produto){
        return new ResponseEntity<>(produtoService.novoProduto(produto), HttpStatus.CREATED);
    }
	
	@PutMapping
    public ResponseEntity<Void> atualizarProduto(@RequestBody ProdutoPutRequestBody produto){
		produtoService.atualizarProduto(produto);
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
	
	@PutMapping(path = "/desativar/{id}")
    public ResponseEntity<Void> desativarProdutosDeUmaLoja(@PathVariable Long lojaId){
		produtoService.desativarProdutosDeUmaLoja(lojaId);
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
	
	@PutMapping(path = "/ativar/{id}")
    public ResponseEntity<Void> ativarProdutosDeUmaLoja(@PathVariable Long lojaId){
		produtoService.ativarProdutosDeUmaLoja(lojaId);
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
	
	@DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
		produtoService.deletarProduto(id);
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

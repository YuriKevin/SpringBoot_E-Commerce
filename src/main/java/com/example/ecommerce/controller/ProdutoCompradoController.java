package com.example.ecommerce.controller;
import java.util.List;
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
import com.example.ecommerce.dto.ProdutoCompradoDTO;
import com.example.ecommerce.requests.ProdutoCompradoPostRequestBody;
import com.example.ecommerce.service.ProdutoCompradoService;
import lombok.RequiredArgsConstructor;

@Component
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("/compra")
public class ProdutoCompradoController {
	private final ProdutoCompradoService produtoCompradoService;
	
	@GetMapping(path = "/loja/{lojaId}")
    public ResponseEntity<List<ProdutoCompradoDTO>> listarProdutosVendidosPorUmaLoja(@PathVariable Long lojaId, @RequestParam int pagina){
        return ResponseEntity.ok(produtoCompradoService.listarProdutosVendidosPorUmaLoja(lojaId, pagina));
    }
	
	@GetMapping(path = "/usuario/{usuarioId}")
    public ResponseEntity<List<ProdutoCompradoDTO>> listarProdutosCompradosPorUmUsuario(@PathVariable Long usuarioId, @RequestParam int pagina){
        return ResponseEntity.ok(produtoCompradoService.listarProdutosCompradosPorUmUsuario(usuarioId, pagina));
    }
	
	@GetMapping(path = "/produto/{id}")
    public ResponseEntity<ProdutoCompradoDTO> encontrarProdutoComprado(@PathVariable Long id){
        return ResponseEntity.ok(produtoCompradoService.encontrarPorIdDTO(id));
    }
	
	@PostMapping(path = "/{id}")
    public ResponseEntity<List<ProdutoCompradoDTO>> novaCompra(@PathVariable Long id, @RequestBody @Valid List<ProdutoCompradoPostRequestBody> produtos){
        return new ResponseEntity<>(produtoCompradoService.novaCompra(id, produtos), HttpStatus.CREATED);
    }
	
	@PutMapping(path = "avaliarProduto/{id}")
    public ResponseEntity<Void> avaliarProdutoComprado(@PathVariable Long id, @RequestParam String avaliacao){
		produtoCompradoService.avaliarProdutoComprado(id, Integer.parseInt(avaliacao));
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
	
	@PutMapping(path = "/cancelarCompra/{id}")
    public ResponseEntity<Void> cancelarCompra(@PathVariable Long id){
		produtoCompradoService.cancelarCompra(id);
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
	
}

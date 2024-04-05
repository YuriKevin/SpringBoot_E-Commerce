package com.example.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.example.ecommerce.dto.LojaDTO;
import com.example.ecommerce.model.Categoria;
import com.example.ecommerce.model.DetalhesPreConfigurados;
import com.example.ecommerce.model.Loja;
import com.example.ecommerce.model.Produto;
import com.example.ecommerce.model.Usuario;
import com.example.ecommerce.repository.LojaRepository;
import com.example.ecommerce.requests.LojaPostRequestBody;
import com.example.ecommerce.requests.LojaPutRequestBody;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LojaService {
	private final LojaRepository lojaRepository;
	
	@Transactional
	public Loja encontrarPorIdOuExcecao(Long id){
		return lojaRepository.findById(id)
        		.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Loja Não encontrada"));
	}
	
	@Transactional
	public Loja encontrarPorCodigoLogin(Long id){
		return lojaRepository.findByCodigoLogin(id);
	}
	
	@Transactional
	public Loja encontrarPorCodigoLoginOuExcecao(Long id){
		Loja loja = lojaRepository.findByCodigoLogin(id);
		if(loja==null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Loja com o código informado não existe.");
		}
		return loja;
	}
	
	@Transactional
	public List<LojaDTO> encontrarPorNome(String nome, int pagina){
		List<Loja> lojasSalvas = lojaRepository.findByNomeContainingIgnoreCaseOrderByQuantidadeVendidaDesc(nome, PageRequest.of(pagina, 18));
		verificarListaVaziaExcecao(lojasSalvas);
		List<LojaDTO> lojasDTO = transformarEmDTO(lojasSalvas);
		return lojasDTO;
	}
	
	@Transactional
	public List<LojaDTO> listarLojasMaisPopulares() {
		List<Loja> lojasSalvas = lojaRepository.findTop20ByOrderByQuantidadeVendidaDesc();
		verificarListaVaziaExcecao(lojasSalvas);
		List<LojaDTO> lojasDTO = transformarEmDTO(lojasSalvas);
		return lojasDTO;
	}
	
	@Transactional
	public Loja cadastrar(LojaPostRequestBody loja){
		if(encontrarPorCodigoLogin(loja.getCodigoLogin()) != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este código de login já está sendo utilizado.");
		}
		validarNome(loja.getNome());
		return lojaRepository.save(Loja.builder()
				.codigoLogin(loja.getCodigoLogin())
				.nome(loja.getNome())
				.senha(loja.getSenha())
				.logo(loja.getLogo())
				.quantidadeVendida(0L)
				.credito(0D)
				.build());
	}
	
	public void validarNome(String nome){
		if(nome==null || nome.isEmpty() || nome.length()<3) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome inválido.");
		}
	}
	
	@Transactional
	public Loja login(Long codigoLogin, String senha){
		Loja loja = encontrarPorCodigoLoginOuExcecao(codigoLogin);
		if(loja.getSenha().equals(senha)) {
			return loja;
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Código ou senha incorretos.");
	}
	
	@Transactional
	public void atualizarDados(LojaPutRequestBody loja) {
		Loja lojaSalva = encontrarPorIdOuExcecao(loja.getId());
		lojaSalva.setNome(loja.getNome());
		lojaSalva.setCodigoLogin(loja.getCodigoLogin());
		lojaSalva.setLogo(loja.getLogo());
		lojaRepository.save(lojaSalva);
	}
	
	@Transactional
	public void adicionarDetalhePreConfigurado(Long id, DetalhesPreConfigurados detalhes) {
		Loja lojaSalva = encontrarPorIdOuExcecao(id);
		lojaSalva.getDetalhesPreConfigurados().add(detalhes);
		lojaRepository.save(lojaSalva);
	}
	
	@Transactional
	public void removerDetalhePreConfigurado(Long id, DetalhesPreConfigurados detalhes) {
		Loja lojaSalva = encontrarPorIdOuExcecao(id);
		if(lojaSalva.getDetalhesPreConfigurados().contains(detalhes)) {
			lojaSalva.getDetalhesPreConfigurados().remove(detalhes);
			lojaRepository.save(lojaSalva);
		}
		throw new RuntimeException("O detalhe pré-configurado não existe na lista da loja.");
	}
	
	@Transactional
	public void adicionarQuantidadeVendida(Loja loja, int quantidade) {
		loja.setQuantidadeVendida(loja.getQuantidadeVendida() + quantidade);
		lojaRepository.save(loja);
	}
	
	@Transactional
	public void adicionarCategoria(Long id, Categoria categoria) {
		Loja loja = encontrarPorIdOuExcecao(id);
		loja.getCategorias().add(categoria);
		lojaRepository.save(loja);
	}
	
	@Transactional
	public void removerCategoria(Long id, Categoria categoria) {
		Loja loja = encontrarPorIdOuExcecao(id);
		loja.getCategorias().remove(categoria);
		lojaRepository.save(loja);
	}
	
	@Transactional
	public void adicionarCredito(Loja loja, double credito) {
		loja.setCredito(loja.getCredito() + credito);
		lojaRepository.save(loja);
	}
	
	@Transactional
	public void removerCredito(Long id, double credito) {
		Loja loja = encontrarPorIdOuExcecao(id);
		if(loja.getCredito()<credito) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Crédito insuficiente.");
		}
		loja.setCredito(loja.getCredito()-credito);
		lojaRepository.save(loja);
	}
	
	public List<LojaDTO> transformarEmDTO(List<Loja> lojas){
		List<LojaDTO> LojasDTO = new ArrayList<>();
		for(Loja lojaSalva : lojas) {
			LojaDTO lojaDTO = LojaDTO.builder()
					.id(lojaSalva.getId())
					.nome(lojaSalva.getNome())
					.logo(lojaSalva.getLogo())
					.quantidadeVendida(lojaSalva.getQuantidadeVendida())
					.avaliacao(lojaSalva.getAvaliacao())
					.build();
			LojasDTO.add(lojaDTO);
		}
		return LojasDTO;
	}
	
	public void verificarListaVaziaExcecao(List<Loja> lojas) {
		if(lojas.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "0 resultados encontrados para a pesquisa.");
		}
	}
	
	@Transactional
	public void atualizarSenha(Long id, String senha, String senhaAntiga) {
		Loja loja = encontrarPorIdOuExcecao(id);
		if(loja.getSenha() != senhaAntiga) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A senha enterior está incorreta.");
		}
		loja.setSenha(senha);
		lojaRepository.save(loja);
	}
	
	@Transactional
	public List<LojaDTO> listarLojas(int pagina){
		List<Loja> lojas = lojaRepository.findLojasOrderByQuantidadeVendidaDesc(PageRequest.of(pagina, 18));
		return transformarEmDTO(lojas);
	}
	
	@Transactional
	public void avaliarLoja(Loja loja, int avaliacao) {
		loja.setQuantidadeAvaliacoes(loja.getQuantidadeAvaliacoes()+1);
		loja.setSomaAvaliacoes(loja.getSomaAvaliacoes()+avaliacao);
		loja.setAvaliacao(calcularAvaliacao(loja.getSomaAvaliacoes(), loja.getQuantidadeAvaliacoes()));
		lojaRepository.save(loja);
	}
	
	public Double calcularAvaliacao(Long soma, Long quantidade) {
		return (double) (soma/quantidade);
	}
	
}

package com.example.ecommerce.service;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.example.ecommerce.model.Usuario;
import com.example.ecommerce.repository.UsuarioRepository;
import com.example.ecommerce.requests.UsuarioPostRequestBody;
import com.example.ecommerce.requests.UsuarioPutRequestBody;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
	private final UsuarioRepository usuarioRepository;
	
	@Transactional
	public Usuario encontrarPorIdOuExcecao(Long id) {
		return usuarioRepository.findById(id).
				orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não encontrado"));
	}
	
	@Transactional
	public Usuario encontrarPorEmail(String email) {
		return usuarioRepository.findByEmail(email);
	}
	
	@Transactional
	public Usuario encontrarPorEmailOuExcecao(String email) {
		Usuario usuario = usuarioRepository.findByEmail(email);
		if(usuario==null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário com o email informado não existe.");
		}
		return usuario;
	}
	
	@Transactional
	public Usuario cadastrar(UsuarioPostRequestBody usuario) {
		if(encontrarPorEmail(usuario.getEmail()) != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário com o email informado já existe.");
		}
		return usuarioRepository.save(Usuario.builder()
				.nome(usuario.getNome())
				.senha(usuario.getSenha())
				.email(usuario.getEmail())
				.credito(0D)
				.build());		
	}
	
	@Transactional
	public Usuario login(String email, String senha) {
		Usuario usuario = encontrarPorEmailOuExcecao(email);
		if(usuario.getSenha().equals(senha)) {
			return usuario;
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário ou senha incorretos.");
	}
	
	@Transactional
	public void atualizarDados(UsuarioPutRequestBody usuario) {
		Usuario usuarioSalvo = encontrarPorIdOuExcecao(usuario.getId());
		validarNome(usuario.getNome());
		usuarioSalvo.setNome(usuario.getNome());
		usuarioSalvo.setEmail(usuario.getEmail());
		usuarioRepository.save(usuarioSalvo);
	}
	
	public void validarNome(String nome){
		if(nome==null || nome.isEmpty() || nome.length()<3) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome inválido.");
		}
	}
	
	@Transactional
	public void adicionarCredito(Long id, double credito) {
		Usuario usuario = encontrarPorIdOuExcecao(id);
		usuario.setCredito(usuario.getCredito()+credito);
		usuarioRepository.save(usuario);
	}
	
	@Transactional
	public void removerCredito(Long id, double credito) {
		Usuario usuario = encontrarPorIdOuExcecao(id);
		if(usuario.getCredito()<credito) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Crédito insuficiente.");
		}
		usuario.setCredito(usuario.getCredito()-credito);
		usuarioRepository.save(usuario);
	}
	
	@Transactional
	public void atualizarSenha(Long id, String senha, String senhaAntiga) {
		Usuario usuario = encontrarPorIdOuExcecao(id);
		if(usuario.getSenha() != senhaAntiga) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A senha enterior está incorreta.");
		}
		usuario.setSenha(senha);
		usuarioRepository.save(usuario);
	}
	
	
	
}

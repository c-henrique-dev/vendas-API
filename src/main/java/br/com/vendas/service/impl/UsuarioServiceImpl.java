package br.com.vendas.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vendas.dtos.UsuarioDto;
import br.com.vendas.dtos.UsuarioLogadoDto;
import br.com.vendas.entities.Endereco;
import br.com.vendas.entities.Usuario;
import br.com.vendas.exceptions.NaoEncontradoException;
import br.com.vendas.repositories.UsuarioRepository;
import br.com.vendas.service.interfaces.UsuarioService;

@Service
public class UsuarioServiceImpl implements UserDetailsService, UsuarioService {

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private UsuarioRepository repository;

	@Transactional
	@Override
	public Usuario salvar(UsuarioDto usuarioDto) {
		Usuario usuario = new Usuario();
		BeanUtils.copyProperties(usuarioDto, usuario);

		Endereco endereco = new Endereco();
		BeanUtils.copyProperties(usuarioDto.getEndereco(), endereco);
		usuario.setEndereco(endereco);
		return repository.save(usuario);
	}

	public UserDetails autenticar(UsuarioDto usuarioDto) {
		UserDetails user = loadUserByUsername(usuarioDto.getLogin());
		boolean senhasBatem = encoder.matches(usuarioDto.getSenha(), user.getPassword());

		if (senhasBatem) {
			return user;
		} else {
			throw new NaoEncontradoException("Usuário não encontrado na base de dados.");
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println(username);
		Usuario usuario = repository.findByLogin(username)
				.orElseThrow(() -> new NaoEncontradoException("Usuário não encontrado na base de dados."));

		String[] roles = usuario.isAdmin() ? new String[] { "ADMIN", "USER" } : new String[] { "USER" };

		return User.builder().username(usuario.getLogin()).password(usuario.getSenha()).roles(roles).build();

	}

	@Override
	public void update(Integer id, UsuarioDto usuarioDto) {
		repository.findById(id).map(usuarioExistente -> {
			BeanUtils.copyProperties(usuarioDto, usuarioExistente);
			BeanUtils.copyProperties(usuarioDto.getEndereco(), usuarioExistente.getEndereco());
			usuarioExistente.setEndereco(usuarioExistente.getEndereco());
			repository.save(usuarioExistente);
			return usuarioExistente;
		}).orElseThrow(() -> new NaoEncontradoException("Usuário não encontrado"));
	}

	@Override
	public Usuario listarPorLogin(String login) {
		return this.repository.findByLogin(login)
				.orElseThrow(() -> new NaoEncontradoException("Usuário não encontrado."));
	}

	@Override
	public UsuarioLogadoDto obterUsuarioLogado() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			Usuario usuario = listarPorLogin(authentication.getName());
			return UsuarioLogadoDto.builder().login(usuario.getLogin()).id(usuario.getId()).build();
		}
		return null;
	}

}

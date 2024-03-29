package br.com.vendas.service.interfaces;

import org.springframework.security.core.userdetails.UserDetails;

import br.com.vendas.dtos.UsuarioDto;
import br.com.vendas.dtos.UsuarioLogadoDto;
import br.com.vendas.entities.Usuario;

public interface UsuarioService {
    Usuario salvar(UsuarioDto usuarioDto);

    void update(Integer id, UsuarioDto usuarioDto);

    UserDetails loadUserByUsername(String username);

    Usuario listarPorLogin(String login);

    UsuarioLogadoDto obterUsuarioLogado();

    UserDetails autenticar(UsuarioDto usuarioDto);
}

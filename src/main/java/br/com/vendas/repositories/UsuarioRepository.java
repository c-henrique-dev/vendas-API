package br.com.vendas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vendas.entities.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByLogin(String login);

}

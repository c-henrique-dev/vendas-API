package br.com.vendas.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vendas.entities.Produto;

public interface ProdutoRepository extends JpaRepository<Produto,Integer> {
    Optional<Produto> findByDescricao(String descricao);
}

package br.com.vendas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vendas.entities.Estoque;

public interface EstoqueRepository extends JpaRepository<Estoque, Integer> {

}

package br.com.vendas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vendas.entities.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {

}

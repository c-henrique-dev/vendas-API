package br.com.vendas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vendas.entities.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByPedidoId(Integer id);
}

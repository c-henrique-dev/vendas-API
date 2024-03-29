package br.com.vendas.service.interfaces;

import java.util.List;

import br.com.vendas.entities.Item;

public interface ItemService {
    List<Item> obterItens(Integer id);
}

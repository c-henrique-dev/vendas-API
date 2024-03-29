package br.com.vendas.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.vendas.entities.Item;
import br.com.vendas.exceptions.NaoEncontradoException;
import br.com.vendas.repositories.ItemRepository;
import br.com.vendas.service.interfaces.ItemService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemServiceImple implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    public List<Item> obterItens(Integer id) {
        List<Item> itens = this.itemRepository.findByPedidoId(id);
        if (!itens.isEmpty()) {
            return itens;
        } else {
            throw new NaoEncontradoException("Item não encontrado");
        }
    }
}

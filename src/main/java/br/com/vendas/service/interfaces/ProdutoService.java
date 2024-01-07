package br.com.vendas.service.interfaces;

import br.com.vendas.dtos.ProdutoDto;
import br.com.vendas.entities.Produto;

import java.util.List;

public interface ProdutoService {
    Produto salvar(ProdutoDto produtoDto);
    void update(Integer id, ProdutoDto produtoDto);
    void delete(Integer id);
    Produto getById(Integer id) throws Exception;
    List<Produto> find(Produto filtro) throws Exception;
}

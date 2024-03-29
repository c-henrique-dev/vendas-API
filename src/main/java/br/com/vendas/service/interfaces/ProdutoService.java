package br.com.vendas.service.interfaces;

import br.com.vendas.dtos.MensagemDto;
import br.com.vendas.dtos.ProdutoDto;
import br.com.vendas.entities.Produto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ProdutoService {
    Produto salvar(ProdutoDto produtoDto);

    void update(Integer id, ProdutoDto produtoDto);

    void delete(Integer id);

    Produto getById(Integer id) throws Exception;

    Page<Produto> find(Produto filtro, Pageable pageable) throws Exception;

    MensagemDto adicionarImagemAoProduto(Integer idProduto, MultipartFile imagem) throws Exception;
}

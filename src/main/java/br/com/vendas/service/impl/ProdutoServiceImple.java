package br.com.vendas.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.vendas.dtos.EstoqueDto;
import br.com.vendas.dtos.MensagemDto;
import br.com.vendas.dtos.ProdutoDto;
import br.com.vendas.entities.Estoque;
import br.com.vendas.entities.Produto;
import br.com.vendas.exceptions.NaoEncontradoException;
import br.com.vendas.repositories.ProdutoRepository;
import br.com.vendas.service.interfaces.ProdutoService;

@Service
public class ProdutoServiceImple implements ProdutoService {

    private ProdutoRepository produtoRepository;
    private MinioServiceImple minioServiceImple;

    public ProdutoServiceImple(
            ProdutoRepository produtoRepository,
            MinioServiceImple minioServiceImple) {
        this.produtoRepository = produtoRepository;
        this.minioServiceImple = minioServiceImple;
    }

    @Override
    public Produto salvar(ProdutoDto produtoDto) {
        Produto produto = new Produto();
        BeanUtils.copyProperties(produtoDto, produto);
        
        EstoqueDto estoqueDto = produtoDto.getEstoque();
        Estoque estoque = new Estoque();
        BeanUtils.copyProperties(estoqueDto, estoque);

        produto.setEstoque(estoque);
        
        this.produtoRepository.save(produto);
        return produto;
    }

    public MensagemDto adicionarImagemAoProduto(Integer idProduto, MultipartFile imagem) throws Exception {
        Produto produto = this.produtoRepository.findById(idProduto)
                .orElseThrow(() -> new NaoEncontradoException("Produto não encontrado"));
        String nomeImagem = this.minioServiceImple.enviarImagem(imagem);
        produto.setNomeImagem(nomeImagem);
        this.produtoRepository.save(produto);
        return MensagemDto.builder().mensagem("Upload efetuado para o Minio.").build();
    }

    @Override
    public void update(Integer id, ProdutoDto produtoDto) {
        this.produtoRepository.findById(id)
                .map(p -> {
                    BeanUtils.copyProperties(produtoDto, p);
                    
                    BeanUtils.copyProperties(produtoDto.getEstoque(), p.getEstoque());
                    produtoRepository.save(p);
                    return p;
                }).orElseThrow(() -> new NaoEncontradoException("Produto não encontrado"));

    }

    @Override
    public void delete(Integer id) {
        this.produtoRepository
                .findById(id)
                .map( p -> {
                    produtoRepository.delete(p);
                    return Void.TYPE;
                }).orElseThrow( () ->
                        new NaoEncontradoException("Produto não encontrado."));
    }

    @Override
    public Produto getById(Integer id) throws Exception {
        Produto produto = this.produtoRepository
                .findById(id)
                .orElseThrow( () ->
                        new NaoEncontradoException("Produto não encontrado."));
        if(produto.getNomeImagem() != null && !produto.getNomeImagem().isEmpty()) {
            String imagem = this.minioServiceImple.recuperarImagem(produto.getNomeImagem());
            produto.setNomeImagem(imagem);
        } else {
            String imagem = this.minioServiceImple.recuperarImagem("sem-fundo.png");
            produto.setNomeImagem(imagem);
        }
        
        return produto;
    }

    @Override
    public List<Produto> find(Produto filtro) throws Exception {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING );

        Example<Produto> example = Example.of(filtro, matcher);
        List<Produto> produtos = produtoRepository.findAll(example);
        
        for(Produto p: produtos ) {
            if(p.getNomeImagem() != null && !p.getNomeImagem().isEmpty()) {
                String imagem = this.minioServiceImple.recuperarImagem(p.getNomeImagem());
                p.setNomeImagem(imagem);
            } else {
                String imagem = this.minioServiceImple.recuperarImagem("sem-fundo.png");
                p.setNomeImagem(imagem);
            }
        }

        return produtos;
    }

}
    

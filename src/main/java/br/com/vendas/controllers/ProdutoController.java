package br.com.vendas.controllers;

import br.com.vendas.dtos.MensagemDto;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import br.com.vendas.dtos.ProdutoDto;
import br.com.vendas.entities.Produto;
import br.com.vendas.service.impl.ProdutoServiceImple;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/produtos")
@Tag(name = "Produto")
@CrossOrigin
public class ProdutoController {

    private ProdutoServiceImple produtoService;

    public ProdutoController(ProdutoServiceImple produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Produto save(@RequestBody @Valid ProdutoDto produtoDto) {
        return this.produtoService.salvar(produtoDto);
    }

    @PostMapping(value = "{idProduto}", consumes = {"multipart/form-data"})
    @ResponseStatus(OK)
    public MensagemDto adicionarImagemAoProduto(@PathVariable Integer idProduto, @RequestPart() MultipartFile imagem) throws Exception {
        return this.produtoService.adicionarImagemAoProduto(idProduto, imagem);
    }

    @PutMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void update(@PathVariable Integer id,
                       @RequestBody @Valid ProdutoDto produtoDto) {
        this.produtoService.update(id, produtoDto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        this.produtoService.delete(id);
    }

    @GetMapping("{id}")
    @ResponseStatus(OK)
    public Produto getById(@PathVariable Integer id) throws Exception {
        return this.produtoService.getById(id);
    }

    @GetMapping
    @ResponseStatus(OK)
    public Page<Produto> find(
            @RequestParam(name = "nomeProduto", required = false) String nomeProduto,
            Pageable pageable
    ) throws Exception {
        Produto produto = new Produto(nomeProduto);
        Page<Produto> produtosPage = this.produtoService.find(produto, pageable);
        return produtosPage;
    }

}

package br.com.vendas.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vendas.dtos.ItemDto;
import br.com.vendas.dtos.PagamentoDto;
import br.com.vendas.dtos.PedidoDto;
import br.com.vendas.dtos.StatusPedidoDto;
import br.com.vendas.entities.Item;
import br.com.vendas.entities.Pedido;
import br.com.vendas.entities.Produto;
import br.com.vendas.entities.Usuario;
import br.com.vendas.enums.StatusPedido;
import br.com.vendas.exceptions.NaoEncontradoException;
import br.com.vendas.exceptions.RegraNegocioException;
import br.com.vendas.exceptions.StatusPedidoException;
import br.com.vendas.repositories.PedidoRepository;
import br.com.vendas.repositories.ProdutoRepository;
import br.com.vendas.repositories.UsuarioRepository;
import br.com.vendas.service.interfaces.PagamentoService;
import br.com.vendas.service.interfaces.PedidoService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProdutoRepository produtosRepository;
    private final PagamentoService pagamentoService;

    @Override
    @Transactional
    public Pedido salvar(PedidoDto pedidoDto) {
        Integer idUsuario = pedidoDto.getUsuario();
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RegraNegocioException("Código de usuário inválido."));

        Pedido pedido = new Pedido();
        pedido.setDataPedido(LocalDate.now());
        pedido.setUsuario(usuario);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<Item> itemsPedido = converterItens(pedido, pedidoDto.getItens());
        pedido.setItens(itemsPedido);
        for (Item item : pedido.getItens()) {
            pedido.setTotal(item.getProduto().getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade())));

        }

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        if (pedidoSalvo != null) {
            for (Item item : itemsPedido) {
                Integer quantidadeDisponivel = item.getProduto().getEstoque().getQuantidadeDisponivel();
                Integer quantidadeAtualizada = quantidadeDisponivel - item.getQuantidade();
                if (quantidadeAtualizada <= 0) {
                    throw new RegraNegocioException(
                            "Estoque indisponível para o produto: " + item.getProduto().getDescricao());
                } else {
                    item.getProduto().getEstoque().setQuantidadeDisponivel(quantidadeAtualizada);
                    PagamentoDto pagamentoDto = new PagamentoDto();
                    pagamentoDto.setFormaDePagamento(pedidoDto.getFormaDePagamento());
                    pagamentoDto.setValorPagamento(pedido.getTotal());
                    pagamentoDto.setPedido(item.getPedido());
                    pagamentoDto.setParcelas(pedidoDto.getParcelas());
                    this.pagamentoService.realizarPagamento(pagamentoDto);
                }
            }
        }

        return pedido;
    }

    @Override
    public List<Pedido> obterPedidoCompleto(String login) {
        return pedidoRepository.findByIdFetchItens(login);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedidoDto statusPedidoDto) {
        try {
            StatusPedido statusConvertido = StatusPedido.valueOf(statusPedidoDto.getStatusPedido().toUpperCase());
            this.pedidoRepository.findById(id).map(pedido -> {
                pedido.setStatus(statusConvertido);
                return pedidoRepository.save(pedido);
            }).orElseThrow(() -> new NaoEncontradoException("Pedido não encontrado"));
        } catch (IllegalArgumentException e) {
            throw new StatusPedidoException("Status inválido");
        }
    }

    private List<Item> converterItens(Pedido pedido, List<ItemDto> items) {
        if (items.isEmpty()) {
            throw new RegraNegocioException("Não é possível realizar um pedido sem items.");
        }

        return items.stream().map(dto -> {
            Integer idProduto = dto.getProduto();
            Produto produto = produtosRepository.findById(idProduto)
                    .orElseThrow(() -> new RegraNegocioException("Código de produto inválido: " + idProduto));

            Item itemPedido = new Item();
            itemPedido.setQuantidade(dto.getQuantidade());
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(produto);
            return itemPedido;
        }).collect(Collectors.toList());

    }

}

package br.com.vendas.service.interfaces;

import java.util.List;

import br.com.vendas.dtos.PedidoDto;
import br.com.vendas.dtos.StatusPedidoDto;
import br.com.vendas.entities.Pedido;

public interface PedidoService {
    Pedido salvar( PedidoDto dto );
    List<Pedido> obterPedidoCompleto(String login);
    void atualizaStatus(Integer id, StatusPedidoDto statusPedido);
}

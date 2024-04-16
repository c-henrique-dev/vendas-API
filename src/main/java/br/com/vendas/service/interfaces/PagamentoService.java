package br.com.vendas.service.interfaces;

import br.com.vendas.dtos.PagamentoDto;
import br.com.vendas.entities.Pagamento;

public interface PagamentoService {
    Pagamento realizarPagamento(PagamentoDto pagamentoDto);
}

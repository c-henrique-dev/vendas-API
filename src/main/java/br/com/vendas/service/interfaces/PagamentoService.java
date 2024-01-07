package br.com.vendas.service.interfaces;

import br.com.vendas.dtos.MensagemDto;
import br.com.vendas.dtos.PagamentoDto;

public interface PagamentoService {
	MensagemDto realizarPagamento(PagamentoDto pagamentoDto);
}

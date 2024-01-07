package br.com.vendas.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import br.com.vendas.dtos.MensagemDto;
import br.com.vendas.dtos.PagamentoDto;
import br.com.vendas.entities.Pagamento;
import br.com.vendas.enums.FormaDePagamento;
import br.com.vendas.enums.StatusPagamento;
import br.com.vendas.repositories.PagamentoRepository;
import br.com.vendas.service.interfaces.PagamentoService;

@Service
public class PagamentoServiceImple implements PagamentoService {

	private PagamentoRepository pagamentoRepository;

	public PagamentoServiceImple(PagamentoRepository pagamentoRepository) {
		this.pagamentoRepository = pagamentoRepository;
	}

	@Override
	public MensagemDto realizarPagamento(PagamentoDto pagamentoDto) {
		Pagamento pagamento = new Pagamento();
		if(pagamentoDto.getFormaDePagamento() != FormaDePagamento.CARTAO_CREDITO) {
			pagamento.setParcela(0);
		} else {
			pagamento.setParcela(pagamentoDto.getParcelas());
		}
		pagamento.setStatusPagamento(StatusPagamento.ANALISE);
		pagamento.setPedido(pagamentoDto.getPedido());
		BeanUtils.copyProperties(pagamentoDto, pagamento);
		this.pagamentoRepository.save(pagamento);
		return MensagemDto.builder().mensagem("O pagamento está em análise!").build();
	}

}

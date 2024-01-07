package br.com.vendas.dtos;

import java.math.BigDecimal;

import br.com.vendas.entities.Pedido;
import br.com.vendas.enums.FormaDePagamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoDto {
	private BigDecimal valorPagamento;
	private FormaDePagamento formaDePagamento;
	private Integer parcelas;
	private Pedido pedido;
}

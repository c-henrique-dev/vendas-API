package br.com.vendas.dtos;

import java.util.List;

import br.com.vendas.enums.FormaDePagamento;
import br.com.vendas.validation.NotEmptyList;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDto {
	@NotNull(message = "{campo.codigo-usuario.obrigatorio}")
	private Integer usuario;
	@NotNull(message = "{campo.forma-pagamento.obrigatorio}")
	private FormaDePagamento formaDePagamento;
	private Integer parcelas;	
	@NotEmptyList(message = "{campo.items-pedido.obrigatorio}")
	private List<ItemDto> itens;
}

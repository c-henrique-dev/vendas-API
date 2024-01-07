package br.com.vendas.dtos;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InformacoesPedidoDto {
    private Integer codigo;
    private String cpf;
    private String nomeUsuario;
    private BigDecimal total;
    private String dataPedido;
    private String status;
    private List<InformacaoItemDto> items;
}

package br.com.vendas.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InformacaoItemDto {
    private String descricaoProduto;
    private BigDecimal precoUnitario;
    private Integer quantidade;
}

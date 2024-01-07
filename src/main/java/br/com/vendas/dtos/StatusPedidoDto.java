package br.com.vendas.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusPedidoDto {
    @NotNull(message = "{campo.tipo-status.obrigatorio}")
    String statusPedido;
}

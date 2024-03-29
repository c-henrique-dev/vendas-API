package br.com.vendas.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstoqueDto {
    @NotNull(message = "{campo.quantidade-disponivel.obrigatorio}")
    private Integer quantidadeDisponivel;
}

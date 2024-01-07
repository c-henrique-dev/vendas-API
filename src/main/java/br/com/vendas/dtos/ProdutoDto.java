package br.com.vendas.dtos;

import java.math.BigDecimal;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDto {
    @Hidden
    private Long id;
    @NotEmpty(message = "{campo.descricao.obrigatorio}")
    private String descricao;
    @NotNull(message = "{campo.preco.obrigatorio}")
    private BigDecimal precoUnitario;
	@NotNull(message = "{campo.estoque.obrigatorio}")
    private EstoqueDto estoque;
    
}

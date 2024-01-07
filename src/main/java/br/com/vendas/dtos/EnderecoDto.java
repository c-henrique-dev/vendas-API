package br.com.vendas.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDto {
	private String rua;
	private String complemento;
	private Integer numero;
	private String cep;
	private String cidade;
	private String estado;
	private String bairro;
}

package br.com.vendas.dtos;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {
	@NotEmpty(message = "{campo.nome.obrigatorio}")
	private String nome;
	@NotEmpty(message = "{campo.cpf.obrigatorio}")
	@CPF(message = "{campo.cpf.invalido}")
	private String cpf;
	@NotEmpty(message = "{campo.login.obrigatorio}")
	private String login;
	@NotEmpty(message = "{campo.senha.obrigatorio}")
	private String senha;
	private Boolean admin;
	private EnderecoDto endereco;
}

package br.com.vendas.entities;

import jakarta.persistence.*;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

	@Column(nullable = false, name = "nome_produto")
    private String nomeProduto;

	@Column(nullable = false)
    private String descricao;

    @Column(nullable = false, name = "preco_unitario")
    private BigDecimal precoUnitario;

    @Column(name = "nome_imagem")
    private String nomeImagem;
    
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Estoque estoque;

    public Produto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

}

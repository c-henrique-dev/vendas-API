package br.com.vendas.entities;

import java.math.BigDecimal;

import br.com.vendas.enums.FormaDePagamento;
import br.com.vendas.enums.StatusPagamento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pagamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(nullable = false, name = "valor_pagamento", precision = 20, scale = 2)
	private BigDecimal valorPagamento;
	
	@Column(nullable = false)
    private Integer parcela;
	
    @Enumerated(EnumType.STRING)
	@Column(nullable = false, name = "forma_pagamento")
	private FormaDePagamento formaDePagamento;
    
	@Column(nullable = false, name = "status_pagamento")
	@Enumerated(EnumType.STRING)
    private StatusPagamento statusPagamento;
    
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
	
}

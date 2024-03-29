package br.com.vendas.exceptions;

public class StatusPedidoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public StatusPedidoException(String message) {
        super(message);
    }

}

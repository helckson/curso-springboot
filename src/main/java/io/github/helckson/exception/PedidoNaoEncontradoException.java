package io.github.helckson.exception;

public class PedidoNaoEncontradoException extends RuntimeException {
	
	public PedidoNaoEncontradoException() {
		super("Pedido não encontrado");
	}
	
}

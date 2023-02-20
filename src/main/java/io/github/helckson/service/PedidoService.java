package io.github.helckson.service;

import java.util.Optional;

import io.github.helckson.domain.entity.Pedido;
import io.github.helckson.domain.entity.enums.StatusPedido;
import io.github.helckson.rest.dto.PedidosDTO;

public interface PedidoService {
	Pedido salvar(PedidosDTO dto);
	
	Optional<Pedido> obterPedidoCompleto(Integer id);
	
	void atualizarStatus(Integer id, StatusPedido statusPedido);
}

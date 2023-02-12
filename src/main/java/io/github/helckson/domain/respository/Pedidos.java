package io.github.helckson.domain.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.helckson.domain.entity.Cliente;
import io.github.helckson.domain.entity.Pedido;

public interface Pedidos extends JpaRepository<Pedido, Integer>{
	
	List<Pedido> findByCliente(Cliente cliente);
}

package io.github.helckson;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.github.helckson.domain.entity.Cliente;
import io.github.helckson.domain.entity.Pedido;
import io.github.helckson.domain.respository.Clientes;
import io.github.helckson.domain.respository.Pedidos;

@SpringBootApplication
public class VendasApplication {
	
	@Bean
	public CommandLineRunner init(
			@Autowired Clientes clientes,
			@Autowired Pedidos pedidos) {
		return args -> {
			System.out.println("Salvando clientes");
			Cliente jose = new Cliente("José");
			clientes.save(jose);
			
			Pedido p = new Pedido();
			p.setCliente(jose);
			p.setDataPedido(LocalDate.now());
			p.setTotal(BigDecimal.valueOf(100));
			
			pedidos.save(p);
			
//			Cliente cliente = clientes.findClienteFetchPedidos(jose.getId());
//			System.out.println(cliente);
//			System.out.println(cliente.getPedidos());
			
			pedidos.findByCliente(jose).forEach(System.out::println);;
			
			
<<<<<<< Updated upstream
=======
			List<Cliente> todosClientes = clientes.obterTodos();
			todosClientes.forEach(System.out::println);
			
			List<Cliente> todossClientes = clientes.obterTodos();
			todosClientes.forEach(System.out::println);
			
			
>>>>>>> Stashed changes
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);

	}
}

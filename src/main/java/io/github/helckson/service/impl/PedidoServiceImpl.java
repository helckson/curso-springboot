package io.github.helckson.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.helckson.domain.entity.Cliente;
import io.github.helckson.domain.entity.ItemPedido;
import io.github.helckson.domain.entity.Pedido;
import io.github.helckson.domain.entity.Produto;
import io.github.helckson.domain.entity.enums.StatusPedido;
import io.github.helckson.domain.respository.Clientes;
import io.github.helckson.domain.respository.ItensPedidos;
import io.github.helckson.domain.respository.Pedidos;
import io.github.helckson.domain.respository.Produtos;
import io.github.helckson.exception.PedidoNaoEncontradoException;
import io.github.helckson.exception.RegraNegocioException;
import io.github.helckson.rest.dto.ItemPedidoDTO;
import io.github.helckson.rest.dto.PedidosDTO;
import io.github.helckson.service.PedidoService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

	private final Pedidos repository;
	private final Clientes clientesRepository;
	private final Produtos produtosRepository;
	private final ItensPedidos itensPedidosRepository;


	@Override
	@Transactional
	public Pedido salvar(PedidosDTO dto) {
		Integer idCliente = dto.getCliente();
		Cliente cliente = clientesRepository.findById(idCliente)
				.orElseThrow(() -> new RegraNegocioException("Codigo de cliente inválido"));
		
		Pedido pedido = new Pedido();
		pedido.setTotal(dto.getTotal());
		pedido.setDataPedido(LocalDate.now());
		pedido.setCliente(cliente);
		pedido.setStatus(StatusPedido.REALIZADO);
		
		List<ItemPedido> itensPedidos = converterItens(pedido, dto.getItens());
		repository.save(pedido);
		itensPedidosRepository.saveAll(itensPedidos);
		pedido.setItens(itensPedidos);
		
		return pedido;
	}
	
	private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDTO> itens) {
		if(itens.isEmpty()) {
			throw new RegraNegocioException("Não é possível relaizar um pedido sem itens.");
		}
		return itens
				.stream()
				.map(dto -> {
					Integer idProduto = dto.getProduto();
					Produto produto = produtosRepository
						.findById(idProduto)
						.orElseThrow(
								() -> new RegraNegocioException(
											"Codigo de produto inválido: "+ idProduto
								));
					
					ItemPedido itemPedido = new ItemPedido();
					itemPedido.setQuantidade(dto.getQuantidade());
					itemPedido.setPedido(pedido);
					itemPedido.setProduto(produto);
					return itemPedido;
				}).collect(Collectors.toList());
	}

	@Override
	public Optional<Pedido> obterPedidoCompleto(Integer id) {
		return repository.findByIdFetchItens(id);
	}

	@Override
	@Transactional
	public void atualizarStatus(Integer id, StatusPedido statusPedido) {
		repository
			.findById(id)
			.map(pedido -> {
				pedido.setStatus(statusPedido);
				return repository.save(pedido);
			}).orElseThrow(() -> new PedidoNaoEncontradoException());
		
	}
	
}

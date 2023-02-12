package io.github.helckson.domain.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.helckson.domain.entity.ItemPedido;

public interface ItensPedidos extends JpaRepository<ItemPedido, Integer> {

}

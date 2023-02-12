package io.github.helckson.domain.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.helckson.domain.entity.Produto;

public interface Produtos extends JpaRepository<Produto, Integer>{

}

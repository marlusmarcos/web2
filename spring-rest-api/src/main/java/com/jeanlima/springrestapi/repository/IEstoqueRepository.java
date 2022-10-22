package com.jeanlima.springrestapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jeanlima.springrestapi.model.Estoque;
import com.jeanlima.springrestapi.model.Produto;

public interface IEstoqueRepository extends JpaRepository<Estoque, Integer> {
	
	//@Query("select p from estoque where p.produto_id = ")
	
	//@Query (value = "select e from Estoque e where e.produto_id = :idproduto")
	Estoque findByProduto (Produto produto);
	
	//Optional<Estoque>  findByid_produto (String nameProduto);

}

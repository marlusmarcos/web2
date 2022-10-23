package com.jeanlima.springrestapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jeanlima.springrestapi.model.Estoque;
import com.jeanlima.springrestapi.model.Produto;

@Repository
public interface IEstoqueRepository extends JpaRepository<Estoque, Integer> {
	
	//@Query("select p from estoque where p.produto_id = ")
	
	//@Query (value = "select e from Estoque e where e.produto_id = :idproduto")
	Estoque findByProduto (Produto produto);
	
	//@Query ("select * from Estoque where Produto.descricao = :descricao")
	//List<Estoque> findByNomeProduto (@Param("descricao") String descricao);
	
	//Optional<Estoque>  findByid_produto (String nameProduto);

}

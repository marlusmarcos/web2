package com.jeanlima.springrestapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeanlima.springrestapi.model.Estoque;
import com.jeanlima.springrestapi.model.Produto;
import com.jeanlima.springrestapi.repository.IEstoqueRepository;

@Service
public class EstoqueService {
	
	@Autowired
	private IEstoqueRepository estoqueRepository;
	
	public Estoque criarEstoque (Estoque estoque) {
		
		return estoqueRepository.save(estoque);
		
		
	} 
	
	public Optional<Estoque> getById(Integer id) {
		Optional<Estoque> estoque;
		estoque = estoqueRepository.findById(id);
		return estoque!=null? estoque : null;
	}
	
	public Estoque updateEstoque (int id, Estoque estoque) {
		Estoque estoqueAtual = estoqueRepository.getById(id);
		estoque.setId(estoqueAtual.getId());
		estoqueRepository.save(estoque);
		return estoque;
	}
	
	public Estoque delete (int id) {
		Estoque estoque = new Estoque();
		estoque = estoqueRepository.getById(id);
		if (estoque != null) {
			estoqueRepository.deleteById(id);
		}
		return null;
	 
	}
	
	public boolean ehValido (Produto p, int qtd) {
		
		Estoque e = buscarPorProduto(p);
		System.out.println("ESTOQUE COM IDPRODUTO = " + e.getProdutos().getId() + "\nCom quantidade = " + e.getQuantidade());
		if (e.getQuantidade() >= qtd) {
			e.setQuantidade(e.getQuantidade() - qtd);
			updateEstoque(e.getId(), e);
			return true;
		}
		
		return false;
	}
	
	public Estoque buscarPorProduto (Produto p ) {
		return estoqueRepository.findByProduto(p);
	}
	
	public List <Estoque> buscarPeloNomeProduto (String nome) {
		return estoqueRepository.findByNomeProduto(nome);
	}
	
	
}

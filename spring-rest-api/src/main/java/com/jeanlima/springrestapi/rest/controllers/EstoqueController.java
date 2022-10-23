package com.jeanlima.springrestapi.rest.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeanlima.springrestapi.model.Estoque;
import com.jeanlima.springrestapi.service.EstoqueService;

@RestController
@RequestMapping ("/api/estoque")
public class EstoqueController {

	@Autowired
	private EstoqueService _estoqueService;
	
	@GetMapping("{id}")
	public Optional<Estoque> buscarPorId (@PathVariable Integer id) {
		return _estoqueService.getById(id);
	}
//	@GetMapping("{descricao}")
//	public List<Estoque> buscarPornomeProduto (@PathVariable String descricao) {
//		return _estoqueService.buscarPeloNomeProduto(descricao);
//	}
//	
	@PostMapping()
	public Estoque createEstoque (@RequestBody Estoque estoque) {
		return _estoqueService.criarEstoque(estoque);
	}
	
	@DeleteMapping("{id}")
	public Estoque deletePorId (@PathVariable int id) {
		return _estoqueService.delete(id);
	}
	@PutMapping("{id}")
	public Estoque alterarEstoque (@PathVariable int id, @RequestBody Estoque estoque) {
		return _estoqueService.updateEstoque(id, estoque);
	}
	
	

}

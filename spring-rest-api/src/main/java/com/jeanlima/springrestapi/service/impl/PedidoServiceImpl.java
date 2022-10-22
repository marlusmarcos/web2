package com.jeanlima.springrestapi.service.impl;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.jeanlima.springrestapi.enums.StatusPedido;
import com.jeanlima.springrestapi.exception.PedidoNaoEncontradoException;
import com.jeanlima.springrestapi.exception.RegraNegocioException;
import com.jeanlima.springrestapi.model.Cliente;
import com.jeanlima.springrestapi.model.ItemPedido;
import com.jeanlima.springrestapi.model.Pedido;
import com.jeanlima.springrestapi.model.Produto;
import com.jeanlima.springrestapi.repository.ClienteRepository;
import com.jeanlima.springrestapi.repository.ItemPedidoRepository;
import com.jeanlima.springrestapi.repository.PedidoRepository;
import com.jeanlima.springrestapi.repository.ProdutoRepository;
import com.jeanlima.springrestapi.rest.dto.ItemPedidoDTO;
import com.jeanlima.springrestapi.rest.dto.PedidoDTO;
import com.jeanlima.springrestapi.service.EstoqueService;
import com.jeanlima.springrestapi.service.PedidoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {
    
    private final PedidoRepository repository;
    private final ClienteRepository clientesRepository;
    private final ProdutoRepository produtosRepository;
    private final ItemPedidoRepository itemsPedidoRepository;
    private final EstoqueService estoqueService;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) throws Exception {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clientesRepository
                .findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido."));

     //   if (estoqueService.ehValido(null, 0))
        Pedido pedido = new Pedido();
        
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);
        BigDecimal preco = new BigDecimal(0);
        
        List<ItemPedido> itemsPedido = converterItems(pedido, dto.getItems());
        preco = preco.add(itemsPedido.get(0).getProduto().getPreco());
      
//        pedido.setTotal(preco);
        for (ItemPedido ip : itemsPedido) {
        	Produto p = ip.getProduto();
        	int qtd = ip.getQuantidade();
        	//preco.add(p.getPreco().multiply(BigDecimal.valueOf(qtd)));
        	preco = preco.add(p.getPreco().multiply(BigDecimal.valueOf(qtd)));
        	System.out.println (preco);
        	pedido.setTotal(preco);
        	if (estoqueService.ehValido(p, qtd)) {
        		continue;
        	} else {
        		throw new Exception("quantidade maior que em estoque, produto esgostado!!");
        	}
        	
        }
     
        repository.save(pedido);
        itemsPedidoRepository.saveAll(itemsPedido);
        pedido.setItens(itemsPedido);
        return pedido;
    }
    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items){
        if(items.isEmpty()){
            throw new RegraNegocioException("Não é possível realizar um pedido sem items.");
        }

        return items
                .stream()
                .map( dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtosRepository
                            .findById(idProduto)
                            .orElseThrow(
                                    () -> new RegraNegocioException(
                                            "Código de produto inválido: "+ idProduto
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
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        repository
                .findById(id)
                .map( pedido -> {
                    pedido.setStatus(statusPedido);
                    return repository.save(pedido);
                }).orElseThrow(() -> new PedidoNaoEncontradoException() );
        
    }
    public Produto buscarPorId (Integer id) {
    	return produtosRepository.getById(id);
    }
}

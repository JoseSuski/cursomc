package com.josesuski.cursomc.services;

import java.util.Date;
import java.util.Optional;

import com.josesuski.cursomc.domain.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.josesuski.cursomc.domain.ItemPedido;
import com.josesuski.cursomc.domain.PagamentoComBoleto;
import com.josesuski.cursomc.domain.Pedido;
import com.josesuski.cursomc.domain.enums.EstadoPagamento;
import com.josesuski.cursomc.repositories.ItemPedidoRepository;
import com.josesuski.cursomc.repositories.PagamentoRepository;
import com.josesuski.cursomc.repositories.PedidoRepository;
import com.josesuski.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repo;
    @Autowired
    private BoletoService boletoService;
    @Autowired
    private PagamentoRepository pagamentoRepository;
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;
    @Autowired
    private ProdutoService produtoService;

    public Pedido find(Integer id) {
        Optional<Pedido> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
    }

    public Pedido insert(Pedido obj) {
        obj.setId(null);
        obj.setInstante(new Date());
        obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);
        if (obj.getPagamento() instanceof PagamentoComBoleto) {
            PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
        }
        obj = repo.save(obj);
        pagamentoRepository.save(obj.getPagamento());
        for (ItemPedido ip : obj.getItens()) {
            ip.setDesconto(0.0);
			Optional<Produto> p = produtoService.find(ip.getProduto().getId());

			ip.setPreco(p.get().getPreco());

			ip.setPedido(obj);
        }
        itemPedidoRepository.saveAll(obj.getItens());
        return obj;
    }
}
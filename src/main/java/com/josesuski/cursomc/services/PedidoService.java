package com.josesuski.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.josesuski.cursomc.domain.Pedido;
import com.josesuski.cursomc.repositories.PedidoRepository;
import com.josesuski.cursomc.services.exceptions.ObjectNoFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	public Optional<Pedido> find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		if(obj == null) {
			throw new ObjectNoFoundException("Objeto não encontrado! Id: " + id + " tipo: " + Pedido.class.getName());
		}
		return obj;
	}
	
}

package com.josesuski.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.josesuski.cursomc.domain.Cliente;
import com.josesuski.cursomc.repositories.ClienteRepository;
import com.josesuski.cursomc.services.exceptions.ObjectNoFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	public Optional<Cliente> buscar(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		if(obj == null) {
			throw new ObjectNoFoundException("Objeto não encontrado! Id: "+ id+" tipo: "+Cliente.class.getName());
		}
		return obj;
	}
	
}

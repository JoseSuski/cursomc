package com.josesuski.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.josesuski.cursomc.domain.Categoria;
import com.josesuski.cursomc.repositories.CategoriaRepository;
import com.josesuski.cursomc.services.exceptions.DataIntegrityException;
import com.josesuski.cursomc.services.exceptions.ObjectNoFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;

	public Optional<Categoria> find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);

		if (!obj.isPresent()) {
			throw new ObjectNoFoundException(
					"Objeto não encontrado! Id: " + id + " tipo: " + Categoria.class.getName());
		}
		return obj;
	}

	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);

	}

	public Categoria update(Categoria obj) {
		find(obj.getId());
		return repo.save(obj);

	}

	public void delete(Integer id) {

		find(id);
		
		try {
		repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria com produtos");
		}
		
	}
	
	public List<Categoria> findAll(){
		
		return repo.findAll();
	}

}

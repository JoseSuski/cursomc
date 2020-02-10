package com.josesuski.cursomc.services;

import com.josesuski.cursomc.domain.Categoria;
import com.josesuski.cursomc.domain.Produto;
import com.josesuski.cursomc.repositories.CategoriaRepository;
import com.josesuski.cursomc.repositories.ProdutoRepository;
import com.josesuski.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repo;

	@Autowired
	private CategoriaRepository categoriaRepository;

    public Optional<Produto> find(Integer id) {
        Optional<Produto> obj = repo.findById(id);
        if (obj == null) {
            throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + " tipo: " + Produto.class.getName());
        }
        return obj;
    }

    public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		return repo.search(nome, categorias, pageRequest);

    }

}

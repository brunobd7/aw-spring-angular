package com.algaworks.algamoney.api.resource;

import com.algaworks.algamoney.api.model.Category;
import com.algaworks.algamoney.api.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryResource {

    //INJETANDO IMPLEMENTACOES DO JPA PARA USO DOS METODOS DEFAULT EX.FINDALL, FIND BY ID.
    @Autowired
    private CategoryRepository categoryRepository;

    //anotacao para mapeamento dos gets
    @GetMapping
    public List<Category> listAll(){
        return categoryRepository.findAll();
    }
}

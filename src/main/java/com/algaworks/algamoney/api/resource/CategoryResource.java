package com.algaworks.algamoney.api.resource;

import com.algaworks.algamoney.api.model.Category;
import com.algaworks.algamoney.api.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
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
   /* @GetMapping
    public ResponseEntity<?> listAll(){ //USANDO REPONSE ENITTY PARA TRATAMETNO DO JSON DE RETORNO COM CODIGOS HTTP
        List<Category> categories = categoryRepository.findAll();
        return !categories.isEmpty() ? ResponseEntity.ok(categories) : ResponseEntity.noContent().build();
    }*/

    //PostMapping anotacao para mapeamento dos metodos que atendente requisicoes POST
    //@RequesteBody usado para pegar valor do body do json de origem do post
    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Category> create(@RequestBody Category category , HttpServletResponse response){
        Category createdCategory = categoryRepository.save(category);

        //ADD AO HEADER E URI A LOCATION COM DADOS DA ENTIDADE SALVA NO BANCO
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(createdCategory.getId()).toUri();

        response.setHeader("Location", uri.toASCIIString());

        //RETORNANDO OBJTO DA ENTITY CRIADA E VALIDANDO O HTTP CODE DE RETORNO , ASSIM NAO SENDO NECESSARIO A ANOTACAO @responseStatus
        return ResponseEntity.created(uri).body(createdCategory);
    }

    @GetMapping("/{id}")
    public Category findByCode(@PathVariable Integer id){
        return categoryRepository.findById(id).orElse(new Category());
    }
}

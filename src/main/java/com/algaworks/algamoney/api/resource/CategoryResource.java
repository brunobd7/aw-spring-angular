package com.algaworks.algamoney.api.resource;

import com.algaworks.algamoney.api.event.ResourceCreatedEvent;
import com.algaworks.algamoney.api.model.Category;
import com.algaworks.algamoney.api.repository.CategoryRepository;
import com.algaworks.algamoney.api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryResource {

    //INJETANDO IMPLEMENTACOES DO JPA PARA USO DOS METODOS DEFAULT EX.FINDALL, FIND BY ID.
    @Autowired
    private CategoryRepository categoryRepository;

    //INJETANDO CLASSE DE NEGOCIO/SERVICO
    @Autowired
    CategoryService categoryService;

    //PUBLICADOR DOS EVENTOS DA APLICACAO
    @Autowired
    private ApplicationEventPublisher publisher;

    // HABILITA CORS - permitindo solicitacoes de origens diferentes da aplicacao atual
//    @CrossOrigin (maxAge = 10 , origins = {"http://localhost:8000"})
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
    public ResponseEntity<Category> create(@Valid @RequestBody Category category , HttpServletResponse response){
        Category createdCategory = categoryRepository.save(category);

        //ADD AO HEADER E URI A LOCATION COM DADOS DA ENTIDADE SALVA NO BANCO
        //USANDO EVENT E LISTNER IMPLEMENTADOS COM RECURSOS DO SPRING E PUBLICANDO/ APLICANDO EVENTO COM O PUBLISHER
        publisher.publishEvent(new ResourceCreatedEvent(this,response,createdCategory.getId()));

        //RETORNANDO OBJTO DA ENTITY CRIADA NO REPONSE BODY  E VALIDANDO O HTTP CODE DE RETORNO , ASSIM NAO SENDO NECESSARIO A ANOTACAO @responseStatus
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    //GET POR PARAMETRO ID
    //@PATHVARIABLE PERMITE QUE O ATRIBUTO PASSADO PELA URI/URL SEJA UTILIZADO
    @GetMapping("/{id}")
    public ResponseEntity findByCode(@PathVariable Integer id){
//        return categoryRepository.findById(id).orElse(new Category());

       Category categoryFounded = categoryRepository.findById(id).orElse(null);
       return categoryFounded != null
               ? ResponseEntity.ok(categoryFounded) : ResponseEntity.notFound().build();

//        Optional <Category> categoryFounded = categoryRepository.findById(id);
//        return categoryFounded.isPresent()
//                ? ResponseEntity.ok(categoryFounded) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Integer id){
        categoryRepository.deleteById(id);
    }


    //UPDATE USING TOTAL ENTITY/OBJECT ATTRIBUTES/FIELDS
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCategory(@PathVariable Integer id , @Valid @RequestBody Category category){
        categoryService.updateCategory(id, category);
    }
}

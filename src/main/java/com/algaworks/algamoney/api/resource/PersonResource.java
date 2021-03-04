package com.algaworks.algamoney.api.resource;

import com.algaworks.algamoney.api.event.ResourceCreatedEvent;
import com.algaworks.algamoney.api.model.Person;
import com.algaworks.algamoney.api.repository.PersonRepository;
import com.algaworks.algamoney.api.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonResource {

    //JPA INJECT DEPENDENCES EX. AUTO IMPLEMENTS CRUD METHODS
    @Autowired
    PersonRepository personRepository;

    //INJETANDO (com AUTOWIRED) A CLASSE SERVICE PERSON COM REGRAS DE NEGOCIOS
    //SERVICE CLASS FOR PERSON ENTITY - CLASSE NEGOCIO / REGRAS DE NEGOCIO
    @Autowired
    PersonService personService;

    //PUBLICADOR DOS EVENTOS DA APLICACAO
    @Autowired
    private ApplicationEventPublisher publisher;

    //NOT RETURNING RESPONSE ENTITY FOR EMPTY COLLECTION // VARIABLE TO BUSINNES RULES
    @GetMapping
    public List<Person> listAll (){
        return personRepository.findAll();
    }

    //PATH VARIABLE BINDING FROM URL
    @GetMapping("/{id}")
    public ResponseEntity<Person> findPersonById(@PathVariable Integer id){

        //TRATAMENTO DA EXCEPTION COM LAMBDA JAVA 8 DIRETO NO RETORNO
        // DO OPTIONAL<PERSON> CASO NAO SEJA ENCONTRADA O OBJETO/PROPRIEDADE/ATRIBUTO
        Person personFounded = personRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));

        //remover validacao ternaria pois ja é tratado no resultado do finById NO optional de retoRNOA CIMA
        //HTTP VERBS TRATAMENT WIHT RETURNING RESPONSE ENTITY TYPING FROM MODEL
        return personFounded != null ? ResponseEntity.ok(personFounded) : ResponseEntity.notFound().build();
    }


    //VALIDING ENTITY WITH @VALID LIB OF SPRING
    //REQUEST BODY TO OBJECT JAVA USING @REQUESTBODY
    @PostMapping
    public ResponseEntity<Person> createPerson(@Valid @RequestBody Person person , HttpServletResponse httpServletResponse){
        //JPA CRUD IMPLEMENTATIONS FROM REPOSITORY INTERFACE
        Person createdPerson = personRepository.save(person);

        //ADD AO HEADER E URI A LOCATION COM DADOS DA ENTIDADE SALVA NO BANCO
        //USANDO EVENT E LISTNER IMPLEMENTADOS COM RECURSOS DO SPRING E PUBLICANDO/ APLICANDO EVENTO COM O PUBLISHER
        publisher.publishEvent(new ResourceCreatedEvent(this,httpServletResponse,createdPerson.getId()));

        //RETURNING HTTP STATUS CREATE AND  CREATED OBJECT JAVA IN JSON IN THE BODY OF RESPONSE
        return  ResponseEntity.status(HttpStatus.CREATED).body(createdPerson);

    }

    //MAPPING DELETE  , DEFINITION OF RESPONSE STATUS FOR SUCCESSFUL OPERATION AND NO DATA TO RETURN
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePerson(@PathVariable Integer id){
        personRepository.deleteById(id);
    }


    //UPDATE DA ENTIDADE COMPLETA REPASSANDO TODOS ATRIBUTOS/CAMPOS
    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Integer id, @Valid @RequestBody Person person){

        //ATUALIZANDO PESSOAS USANDO CLASSE DE SERVICO/NEGOCIO ONDE É VALIDADA AS REGRAS DE NEGOCIOS PERTINENTES A ENTIDADE PERSON
        Person savedPerson = personService.updatePerson(id,person);
        return  ResponseEntity.ok(savedPerson);
    }

    //PUT / UPDATE PARCIAL DE UMA PROPRIEDADE ADD TO PATH OF URI THE NAME OF FIELD/ATTRIBUTE/PROPERTIE
    @PutMapping("/{id}/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePropertieActive(@PathVariable Integer id,@RequestBody Boolean active){
        personService.updatePropertieActive(id,active);
    }

}

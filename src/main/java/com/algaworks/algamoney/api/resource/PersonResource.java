package com.algaworks.algamoney.api.resource;

import com.algaworks.algamoney.api.event.ResourceCreatedEvent;
import com.algaworks.algamoney.api.model.Person;
import com.algaworks.algamoney.api.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonResource {

    //JPA AUTO IMPLEMENTS CRUD METHODS
    @Autowired
    PersonRepository personRepository;

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

        Person personFounded = personRepository.findById(id).orElse(null);
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


}

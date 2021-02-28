package com.algaworks.algamoney.api.resource;

import com.algaworks.algamoney.api.model.Person;
import com.algaworks.algamoney.api.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonResource {

    //JPA AUTO IMPLEMENTS CRUD METHODS
    @Autowired
    PersonRepository personRepository;

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

        //URI WITH LOCATION WHERE ENTITY OBJECT CREATED IN DATABASE
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(createdPerson.getId()).toUri();

        //RETURNING HTTP STATUS CREATE AND  CREATED OBJECT JAVA IN JSON IN THE BODY OF RESPONSE
        return  ResponseEntity.created(uri).body(createdPerson);
    }

}

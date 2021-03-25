package com.algaworks.algamoney.api.service;

import com.algaworks.algamoney.api.model.Person;
import com.algaworks.algamoney.api.repository.PersonRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Objects;

//ANOTACAO DEFINE CLASSE COMO COMPONENTE DO SPRING
//SERVICE CLASS == CLASSE FOR BUSSINESS RULES
@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    public Person updatePerson(Integer id, Person person){

        Person savedPerson = getPersonById(id);

        //BEANUTILS - CLASSE UTILITARIA PARA CLONAR PROPRIEDADES ENTRE OBJETOS
        //copiando/atualizando os dados a serem atualizados de origem no requestBody para o objeto previamente salvo encontrado no banco
        BeanUtils.copyProperties(person,savedPerson,"id");
        return personRepository.save(savedPerson);

    }

    public void updatePropertieActive(Integer id, Boolean active) {
        Person savedPerson = getPersonById(id);
        savedPerson.setActive(active);
        personRepository.save(savedPerson);
    }

    public Person getPersonById(Integer id) {
        //TRATAMENTO DA EXCEPTION COM LAMBDA JAVA 8 DIRETO NO RETORNO
        // DO OPTION<PERSON> CASO NAO SEJA ENCONTRADA O OBJETO/PROPRIEDADE/ATRIBUTO
        Person savedPerson = personRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));

        /*//TRATMENTO NEP PARA TENTATIVA DE UPDATE DE RECURSO (PERSON) nao cadastra/encontrada
        if(Objects.isNull(savedPerson)){
            throw new EmptyResultDataAccessException(1); //ESPERADO MINIMO 1 ELEMENTO OU ATRIBUTO , CALL HANDLER FOR THIS CLASS
        }*/
        return savedPerson;
    }
}

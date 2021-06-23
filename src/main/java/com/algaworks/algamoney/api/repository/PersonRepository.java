package com.algaworks.algamoney.api.repository;

import com.algaworks.algamoney.api.model.Person;
import com.algaworks.algamoney.api.repository.person.PersonRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person,Integer>, PersonRepositoryQuery {

}

package com.algaworks.algamoney.api.repository.person;

import com.algaworks.algamoney.api.model.Person;
import com.algaworks.algamoney.api.repository.filter.PersonFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonRepositoryQuery {

    public Page<Person> filter(PersonFilter personFilter, Pageable pageable);
}

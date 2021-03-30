package com.algaworks.algamoney.api.repository.launch;

import com.algaworks.algamoney.api.model.Launch;
import com.algaworks.algamoney.api.repository.filter.LaunchFilter;

import java.util.List;

//INTERFACE ACESSO JPAREPOSITORY E PERMITIRÁ A CRIACAO DE CRITERIAS DO JPA PARA CONSULTAS PERSONALIZADAS
//NOME DA INTERFACE DEVE OBRIGATORIAMENTE COMECAR COM O NOME DA INTERFACE QUE
// EXTENDE A JPA REPOSITORY neste caso "LaunchRepository"
public interface LaunchRepositoryQuery {

    public List<Launch> filter(LaunchFilter launchFilter);
}

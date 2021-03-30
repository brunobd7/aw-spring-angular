package com.algaworks.algamoney.api.service;

import com.algaworks.algamoney.api.model.Launch;
import com.algaworks.algamoney.api.model.Person;
import com.algaworks.algamoney.api.repository.LaunchRepository;
import com.algaworks.algamoney.api.repository.PersonRepository;
import com.algaworks.algamoney.api.service.exception.NotPresentOrInactivePersonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class LaunchService {

    @Autowired
    private LaunchRepository launchRepository;

    @Autowired
    private PersonRepository personRepository;

    public Launch save(Launch launch){

        Person person = personRepository.findById(launch.getPerson().getId()).orElse(null);

        if( person == null || person.isInactive()){ //TRATAMENTO PARA SOMENTE TER LANCAMENTO POR PESSOAS ATIVAS
            throw new NotPresentOrInactivePersonException();
        }
        return launchRepository.save(launch);
    }

    public Launch findLaunchById(Integer id){
      Launch savedLaunch = launchRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));

      return savedLaunch;
    }
}

package com.algaworks.algamoney.api.service;

import com.algaworks.algamoney.api.model.Launch;
import com.algaworks.algamoney.api.model.Person;
import com.algaworks.algamoney.api.repository.LaunchRespository;
import com.algaworks.algamoney.api.repository.PersonRepository;
import com.algaworks.algamoney.api.service.exception.NotPresentOrInactivePersonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class LaunchService {

    @Autowired
    LaunchRespository launchRespository;

    @Autowired
    private PersonRepository personRepository;

    public Launch save(Launch launch){

        Person person = personRepository.getOne(launch.getPerson().getId());

        if( person == null || person.isInactive()){
            throw new NotPresentOrInactivePersonException();
        }
        return null;
    }

    public Launch findLaunchById(Integer id){
      Launch savedLaunch = launchRespository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));

      return savedLaunch;
    }
}

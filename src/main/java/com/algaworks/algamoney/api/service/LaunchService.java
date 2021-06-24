package com.algaworks.algamoney.api.service;

import com.algaworks.algamoney.api.model.Launch;
import com.algaworks.algamoney.api.model.Person;
import com.algaworks.algamoney.api.repository.LaunchRepository;
import com.algaworks.algamoney.api.repository.PersonRepository;
import com.algaworks.algamoney.api.service.exception.NotPresentOrInactivePersonException;
import org.springframework.beans.BeanUtils;
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

        validatePerson(launch);
        return launchRepository.save(launch);
    }

    public Launch update(Integer id,Launch launchWithUpdates){
        /**TRATANDO CATCH NO REPOSITORY CASO NAO ENCONTRE O REGISTRO NO QUAL SERÁ FEITA A ATUALIZACAO**/
        Launch savedLaunch = launchRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        if(!launchWithUpdates.getPerson().equals(savedLaunch.getPerson()))
            validatePerson(launchWithUpdates);

        /**Copia propriendades do objeto encontrado no banco ignorando ID par ao novo*/
        BeanUtils.copyProperties(launchWithUpdates, savedLaunch,"id");

        return launchRepository.save(savedLaunch);
    }

    private void validatePerson(Launch launch) {
        Person person = personRepository.findById(launch.getPerson().getId()).orElse(null);

        if( person == null || person.isInactive()){ //TRATAMENTO PARA SOMENTE TER LANCAMENTO POR PESSOAS ATIVAS
            throw new NotPresentOrInactivePersonException();
        }
    }

    public Launch findLaunchById(Integer id){
      Launch savedLaunch = launchRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));

      return savedLaunch;
    }
}

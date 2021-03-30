package com.algaworks.algamoney.api.resource;

import com.algaworks.algamoney.api.event.ResourceCreatedEvent;
import com.algaworks.algamoney.api.exceptionhandler.AlgamoneyExceptionHandler;
import com.algaworks.algamoney.api.model.Launch;
import com.algaworks.algamoney.api.repository.LaunchRepository;
import com.algaworks.algamoney.api.repository.filter.LaunchFilter;
import com.algaworks.algamoney.api.service.LaunchService;
import com.algaworks.algamoney.api.service.exception.NotPresentOrInactivePersonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/launches")
public class LaunchResource {

    @Autowired
    LaunchRepository launchRepository; //JPA INJECTION

    @Autowired
    LaunchService launchService; //BUSINESS RULES

    @Autowired
    ApplicationEventPublisher publisher;//SPRING APLICATION EVENT CALL

    @Autowired
    MessageSource messageSource;

    @GetMapping
    public List<Launch> search(LaunchFilter launchFilter){
        return launchRepository.filter(launchFilter);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Launch> findLaunchById(@PathVariable Integer id){
        Launch savedLaunch = launchService.findLaunchById(id); //SERVICE FOR BUSINESS RULES
        return savedLaunch != null ? ResponseEntity.ok(savedLaunch) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Launch> createLaunch(@Valid @RequestBody Launch launch, HttpServletResponse httpServletResponse){

        Launch savedLaunch = launchService.save(launch);

        publisher.publishEvent(new ResourceCreatedEvent(savedLaunch,httpServletResponse, savedLaunch.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(savedLaunch);

    }

    //TRATANDO EXCECAO DE NEGOCIO CRIADA ESPECIFICAMENTE PARA ESSA CONTROLLER NO PACKAGE "SERVICE.EXCEPTION"
    // POR ISSO NAO VAI PARA O PACKAGE EXCPETION HANDLER e class "AlgamoneyExceptionHandler"
    @ExceptionHandler({NotPresentOrInactivePersonException.class})
    public ResponseEntity<Object> handlerNotPresentOrInactivePersonException(NotPresentOrInactivePersonException ex){

        String userMessage = messageSource.getMessage("person.not-present-or-inactive",null, LocaleContextHolder.getLocale());
        String devMessage = ex.toString(); //exception direta , .getCause() nao necessaria
        List<AlgamoneyExceptionHandler.ErroAux> trackedErros = Arrays.asList(new AlgamoneyExceptionHandler.ErroAux(userMessage,devMessage));

        return ResponseEntity.badRequest().body(trackedErros);
    }

}

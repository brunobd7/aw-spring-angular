package com.algaworks.algamoney.api.resource;

import com.algaworks.algamoney.api.event.ResourceCreatedEvent;
import com.algaworks.algamoney.api.model.Launch;
import com.algaworks.algamoney.api.repository.LaunchRespository;
import com.algaworks.algamoney.api.service.LaunchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/launches")
public class LaunchResource {

    @Autowired
    LaunchRespository launchRespository; //JPA INJECTION

    @Autowired
    LaunchService launchService; //BUSINESS RULES

    @Autowired
    ApplicationEventPublisher publisher;//SPRING APLICATION EVENT CALL

    @GetMapping
    public List<Launch> findAllLaunches(){
        return launchRespository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Launch> findLaunchById(@PathVariable Integer id){
        Launch savedLaunch = launchService.findLaunchById(id); //SERVICE FOR BUSINESS RULES
        return savedLaunch != null ? ResponseEntity.ok(savedLaunch) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Launch> createLaunch(@Valid @RequestBody Launch launch, HttpServletResponse httpServletResponse){

        Launch savedLaunch = launchRespository.save(launch);

        publisher.publishEvent(new ResourceCreatedEvent(savedLaunch,httpServletResponse, savedLaunch.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(savedLaunch);

    }
}

package com.algaworks.algamoney.api.service;

import com.algaworks.algamoney.api.model.Launch;
import com.algaworks.algamoney.api.repository.LaunchRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class LaunchService {

    @Autowired
    LaunchRespository launchRespository;

    public Launch findLaunchById(Integer id){
      Launch savedLaunch = launchRespository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));

      return savedLaunch;
    }
}

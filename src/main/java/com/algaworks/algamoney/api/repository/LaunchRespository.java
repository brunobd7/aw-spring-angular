package com.algaworks.algamoney.api.repository;

import com.algaworks.algamoney.api.model.Launch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LaunchRespository extends JpaRepository<Launch,Integer> {
}

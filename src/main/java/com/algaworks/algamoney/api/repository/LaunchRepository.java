package com.algaworks.algamoney.api.repository;

import com.algaworks.algamoney.api.model.Launch;
import com.algaworks.algamoney.api.repository.launch.LaunchRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LaunchRepository extends JpaRepository<Launch,Integer>,LaunchRepositoryQuery {
}

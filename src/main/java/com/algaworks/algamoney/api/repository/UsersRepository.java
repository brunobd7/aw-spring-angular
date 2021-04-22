package com.algaworks.algamoney.api.repository;

import com.algaworks.algamoney.api.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {

    public Optional<Users> findByEmail (String email);

}

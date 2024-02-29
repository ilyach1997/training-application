package com.example.training.repository;

import com.example.training.model.jpa.Account;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {

    Optional<Account> findByUsername(String username);
}

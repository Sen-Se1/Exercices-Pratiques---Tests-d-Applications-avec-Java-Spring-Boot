package com.example.testsdemo.facturation;

import java.util.Optional;

public interface ClientRepository {
    Optional<Client> findById(Long id);
}
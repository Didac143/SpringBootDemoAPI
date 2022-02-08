package com.example.springbootdemoapi.repository;

import com.example.springbootdemoapi.model.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {
    boolean existsClientByNameIgnoreCase(String name);
    List<Client> findClientByNameIgnoreCase(String name);
}

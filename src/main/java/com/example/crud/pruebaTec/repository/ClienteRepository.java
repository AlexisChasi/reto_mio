package com.example.crud.pruebaTec.repository;

import com.example.crud.pruebaTec.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByClienteid(String clienteid);
}
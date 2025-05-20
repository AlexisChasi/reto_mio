package com.example.crud.pruebaTec.service;

import com.example.crud.pruebaTec.model.Cliente;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ClienteService {
    List<Cliente> getClientes();

    Cliente saveOrUpdateCliente(Cliente cliente);

    ResponseEntity<Object> deleteCliente(Long id);
}

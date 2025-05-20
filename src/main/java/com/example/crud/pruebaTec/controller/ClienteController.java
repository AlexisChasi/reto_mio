package com.example.crud.pruebaTec.controller;


import com.example.crud.pruebaTec.model.Cliente;
import com.example.crud.pruebaTec.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<Cliente> getClientes() {
        return clienteService.getClientes();
    }

    @PostMapping
    public Cliente registerCliente(@RequestBody Cliente cliente) {
        return clienteService.saveOrUpdateCliente(cliente);
    }

    @PutMapping
    public Cliente updateCliente(@RequestBody Cliente cliente) {
        return clienteService.saveOrUpdateCliente(cliente);
    }

    @DeleteMapping(path = "{clienteId}")
    public ResponseEntity<Object> deleteCliente(@PathVariable("clienteId") Long id) {
        return clienteService.deleteCliente(id);
    }
}

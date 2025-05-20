package com.example.crud.pruebaTec.service.serviceImpl;

import com.example.crud.pruebaTec.exeption.ResourceConflictException;
import com.example.crud.pruebaTec.exeption.ResourceNotFoundException;
import com.example.crud.pruebaTec.model.Cliente;
import com.example.crud.pruebaTec.repository.ClienteRepository;
import com.example.crud.pruebaTec.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public List<Cliente> getClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente saveOrUpdateCliente(Cliente cliente) {
        Optional<Cliente> existingCliente = clienteRepository.findByClienteid(cliente.getClienteid());

        if (existingCliente.isPresent() && cliente.getId() == null) {
            throw new ResourceConflictException("Ya existe un cliente con ese ID");
        }

        return clienteRepository.save(cliente);
    }

    @Override
    public ResponseEntity<Object> deleteCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("No existe un cliente con ese ID");
        }

        clienteRepository.deleteById(id);
        return null;
    }
}

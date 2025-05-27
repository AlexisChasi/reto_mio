package com.example.crud.pruebaTec.service;

import com.example.crud.pruebaTec.dto.ClienteDto;


import java.util.List;

public interface ClienteService {
    List<ClienteDto> getClientes();
    ClienteDto createCliente(ClienteDto clienteDTO);
    ClienteDto updateCliente(Long id, ClienteDto clienteDTO);
    void deleteCliente(Long id);
}

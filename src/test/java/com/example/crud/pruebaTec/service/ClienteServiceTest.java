package com.example.crud.pruebaTec.service;

import com.example.crud.pruebaTec.dto.ClienteDto;
import com.example.crud.pruebaTec.exeption.ResourceConflictException;
import com.example.crud.pruebaTec.mapper.ClienteMapper;
import com.example.crud.pruebaTec.model.Cliente;
import com.example.crud.pruebaTec.repository.ClienteRepository;
import com.example.crud.pruebaTec.service.serviceImpl.ClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClienteServiceTest {

    private ClienteRepository clienteRepository;
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        clienteRepository = mock(ClienteRepository.class);
        clienteService = new ClienteServiceImpl(clienteRepository);
    }

    @Test
    void CrearClienteExitosamente() {
        ClienteDto dto = ClienteDto.builder()
                .clienteid("nuevo123")
                .nombre("Nuevo Cliente")
                .identificacion("1234567890")
                .direccion("Quito")
                .telefono("0987654321")
                .contrasena("password")
                .estado(true)
                .build();

        when(clienteRepository.findByClienteid("nuevo123")).thenReturn(Optional.empty());
        when(clienteRepository.findByIdentificacion("1234567890")).thenReturn(Optional.empty());

        Cliente cliente = ClienteMapper.toEntity(dto);
        cliente.setId(1L);

        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        ClienteDto result = clienteService.createCliente(dto);

        assertNotNull(result);
        assertEquals("nuevo123", result.getClienteid());
    }

    @Test
    void FallarSiClienteYaExiste() {
        ClienteDto dto = ClienteDto.builder()
                .clienteid("repetido")
                .nombre("Juan")
                .identificacion("111")
                .direccion("Guayaquil")
                .telefono("0999999999")
                .contrasena("abc")
                .estado(true)
                .build();

        when(clienteRepository.findByClienteid("repetido")).thenReturn(Optional.of(new Cliente()));

        assertThrows(ResourceConflictException.class, () -> clienteService.createCliente(dto));
    }
}

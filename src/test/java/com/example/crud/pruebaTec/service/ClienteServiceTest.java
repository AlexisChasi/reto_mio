package com.example.crud.pruebaTec.service;

import com.example.crud.pruebaTec.dto.ClienteDto;
import com.example.crud.pruebaTec.exeption.ResourceConflictException;
import com.example.crud.pruebaTec.exeption.ResourceNotFoundException;
import com.example.crud.pruebaTec.mapper.ClienteMapper;
import com.example.crud.pruebaTec.model.Cliente;
import com.example.crud.pruebaTec.repository.ClienteRepository;
import com.example.crud.pruebaTec.service.serviceImpl.ClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
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
    void crearClienteExitosamente() {
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
        when(clienteRepository.save(any(Cliente.class)))
                .thenReturn(ClienteMapper.toEntity(dto));

        ClienteDto result = clienteService.createCliente(dto);

        assertNotNull(result);
        assertEquals("nuevo123", result.getClienteid());
    }

    @Test
    void errorSiClienteIdYaExiste() {
        ClienteDto dto = ClienteDto.builder()
                .clienteid("repetido")
                .nombre("Juan") // Agregado
                .identificacion("9999999999")
                .direccion("Guayaquil")
                .telefono("0999999999")
                .contrasena("clave")
                .estado(true)
                .build();

        when(clienteRepository.findByClienteid("repetido")).thenReturn(Optional.of(new Cliente()));

        assertThrows(ResourceConflictException.class, () -> clienteService.createCliente(dto));
    }


    @Test
    void errorSiIdentificacionYaExiste() {
        ClienteDto dto = ClienteDto.builder()
                .clienteid("nuevoCliente")
                .nombre("Luis") // Agregado
                .identificacion("2222222222")
                .direccion("Cuenca")
                .telefono("0976543210")
                .contrasena("1234")
                .estado(true)
                .build();

        when(clienteRepository.findByClienteid("nuevoCliente")).thenReturn(Optional.empty());
        when(clienteRepository.findByIdentificacion("2222222222")).thenReturn(Optional.of(new Cliente()));

        assertThrows(ResourceConflictException.class, () -> clienteService.createCliente(dto));
    }


    @Test
    void actualizarClienteExitosamente() {
        ClienteDto dto = ClienteDto.builder()
                .clienteid("cliente01")
                .nombre("Nuevo Nombre")
                .identificacion("9876543210")
                .direccion("Nueva dirección")
                .telefono("0991112233")
                .contrasena("clave")
                .estado(false)
                .build();

        Cliente existing = new Cliente();
        existing.setId(1L);
        existing.setClienteid("cliente01");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(existing));


        Cliente updated = ClienteMapper.toEntity(dto);
        updated.setId(1L);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(updated);

        ClienteDto result = clienteService.updateCliente(1L, dto);

        assertEquals("Nuevo Nombre", result.getNombre());
    }


    @Test
    void eliminarClienteExitosamente() {
        when(clienteRepository.existsById(1L)).thenReturn(true);

        clienteService.deleteCliente(1L);

        verify(clienteRepository).deleteById(1L);
    }

    @Test
    void errorEliminarClienteNoExiste() {
        when(clienteRepository.existsById(999L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> clienteService.deleteCliente(999L));
    }

    @Test
    void obtenerListaDeClientes() {
        Cliente c1 = new Cliente(); c1.setClienteid("c1");
        Cliente c2 = new Cliente(); c2.setClienteid("c2");

        when(clienteRepository.findAll()).thenReturn(List.of(c1, c2));

        List<ClienteDto> result = clienteService.getClientes();

        assertEquals(2, result.size());
        verify(clienteRepository).findAll();
    }
}

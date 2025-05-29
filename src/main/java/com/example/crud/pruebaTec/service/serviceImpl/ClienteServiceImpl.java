package com.example.crud.pruebaTec.service.serviceImpl;

import com.example.crud.pruebaTec.dto.ClienteDto;
import com.example.crud.pruebaTec.exeption.ResourceConflictException;
import com.example.crud.pruebaTec.exeption.ResourceNotFoundException;
import com.example.crud.pruebaTec.mapper.ClienteMapper;
import com.example.crud.pruebaTec.model.Cliente;
import com.example.crud.pruebaTec.repository.ClienteRepository;
import com.example.crud.pruebaTec.service.ClienteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public List<ClienteDto> getClientes() {
        log.info("Obteniendo lista de clientes");
        return clienteRepository.findAll()
                .stream()
                .map(ClienteMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteDto createCliente(ClienteDto clienteDTO) {
        log.info("Creando nuevo cliente con clienteid: {}", clienteDTO.getClienteid());
        validarClienteDto(clienteDTO);

        if (clienteRepository.findByClienteid(clienteDTO.getClienteid()).isPresent()) {
            throw new ResourceConflictException("Ya existe un cliente con ese clienteid");
        }

        if (clienteRepository.findByIdentificacion(clienteDTO.getIdentificacion()).isPresent()) {
            throw new ResourceConflictException("Ya existe un cliente con esa identificación");
        }

        Cliente cliente = ClienteMapper.toEntity(clienteDTO);
        Cliente saved = clienteRepository.save(cliente);
        log.info("Cliente creado exitosamente: ID={}, clienteid={}", saved.getId(), saved.getClienteid());

        return ClienteMapper.toDto(saved);
    }


    @Override
    public ClienteDto updateCliente(Long id, ClienteDto clienteDTO) {
        log.info("Actualizando cliente con ID: {}", id);
        validarClienteDto(clienteDTO);

        Cliente existente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe un cliente con ese ID"));


        existente.setNombre(clienteDTO.getNombre());
        existente.setDireccion(clienteDTO.getDireccion());
        existente.setTelefono(clienteDTO.getTelefono());
        existente.setContrasena(clienteDTO.getContrasena());
        existente.setEstado(clienteDTO.isEstado());



        Cliente saved = clienteRepository.save(existente);
        log.info("Cliente actualizado correctamente: ID={}", saved.getId());
        return ClienteMapper.toDto(saved);
    }


    @Override
    public void deleteCliente(Long id) {
        log.info("Eliminando cliente con ID: {}", id);
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("No existe un cliente con ese ID");
        }
        clienteRepository.deleteById(id);
        log.info("Cliente eliminado correctamente: ID={}", id);
    }

    private void validarClienteDto(ClienteDto dto) {
        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (dto.getDireccion() == null || dto.getDireccion().isBlank()) {
            throw new IllegalArgumentException("La dirección no puede estar vacía");
        }
        if (dto.getTelefono() == null || dto.getTelefono().isBlank()) {
            throw new IllegalArgumentException("El teléfono no puede estar vacío");
        }
        if (dto.getContrasena() == null || dto.getContrasena().isBlank()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }

    }
}

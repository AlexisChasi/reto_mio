package com.example.crud.pruebaTec.mapper;

import com.example.crud.pruebaTec.dto.ClienteDto;
import com.example.crud.pruebaTec.model.Cliente;

public class ClienteMapper {

    public static ClienteDto toDto(Cliente cliente) {
        return ClienteDto.builder()
                .id(cliente.getId())
                .clienteid(cliente.getClienteid())
                .nombre(cliente.getNombre())
                .identificacion(cliente.getIdentificacion())
                .direccion(cliente.getDireccion())
                .telefono(cliente.getTelefono())
                .contrasena(cliente.getContrasena()) // <-- agregado
                .estado(cliente.isEstado())
                .build();
    }

    public static Cliente toEntity(ClienteDto dto) {
        Cliente cliente = new Cliente();
        cliente.setId(dto.getId());
        cliente.setClienteid(dto.getClienteid());
        cliente.setNombre(dto.getNombre());
        cliente.setIdentificacion(dto.getIdentificacion());
        cliente.setDireccion(dto.getDireccion());
        cliente.setTelefono(dto.getTelefono());
        cliente.setContrasena(dto.getContrasena()); //
        cliente.setEstado(dto.isEstado());
        return cliente;
    }

}

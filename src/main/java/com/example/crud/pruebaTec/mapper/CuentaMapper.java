package com.example.crud.pruebaTec.mapper;

import com.example.crud.pruebaTec.dto.CuentaDto;
import com.example.crud.pruebaTec.model.Cliente;
import com.example.crud.pruebaTec.model.Cuenta;

public class CuentaMapper {

    public static CuentaDto toDto(Cuenta cuenta) {
        return CuentaDto.builder()
                .id(cuenta.getId())
                .numeroCuenta(cuenta.getNumeroCuenta())
                .tipoCuenta(cuenta.getTipoCuenta())
                .saldoInicial(cuenta.getSaldoInicial())
                .estado(cuenta.isEstado())
                .clienteId(cuenta.getCliente().getId())
                .build();
    }

    public static Cuenta toEntity(CuentaDto cuentaDto, Cliente cliente) {
        Cuenta cuenta = new Cuenta();
        cuenta.setId(cuentaDto.getId());
        cuenta.setNumeroCuenta(cuentaDto.getNumeroCuenta());
        cuenta.setTipoCuenta(cuentaDto.getTipoCuenta());
        cuenta.setSaldoInicial(cuentaDto.getSaldoInicial());
        cuenta.setEstado(cuentaDto.isEstado());
        cuenta.setCliente(cliente);
        return cuenta;
    }
}
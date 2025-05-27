package com.example.crud.pruebaTec.mapper;

import com.example.crud.pruebaTec.dto.MovimientoDto;
import com.example.crud.pruebaTec.model.Cuenta;
import com.example.crud.pruebaTec.model.Movimiento;

public class MovimientoMapper {

    public static Movimiento toMovimiento(MovimientoDto dto, Cuenta cuenta) {
        Movimiento movimiento = new Movimiento();
        movimiento.setTipoMovimiento(dto.getTipoMovimiento());
        movimiento.setValor(dto.getValor());
        movimiento.setCuenta(cuenta);
        return movimiento;
    }
}

package com.example.crud.pruebaTec.service;

import com.example.crud.pruebaTec.dto.CuentaDto;
import java.util.List;

public interface CuentaService {
    List<CuentaDto> getCuentas();
    CuentaDto createCuenta(CuentaDto cuentaDto);
    CuentaDto updateCuenta(Long id, CuentaDto cuentaDto);
    void deleteCuenta(Long id);
}

package com.example.crud.pruebaTec.service;

import com.example.crud.pruebaTec.model.Cuenta;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CuentaService {
    List<Cuenta> getCuentas();

    Cuenta saveOrUpdateCuenta(Cuenta cuenta);

    ResponseEntity<Object> deleteCuenta(Long id);
}

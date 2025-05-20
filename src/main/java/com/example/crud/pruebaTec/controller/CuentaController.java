package com.example.crud.pruebaTec.controller;

import com.example.crud.pruebaTec.model.Cuenta;
import com.example.crud.pruebaTec.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;

    @Autowired
    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @GetMapping
    public List<Cuenta> getCuentas() {
        return this.cuentaService.getCuentas();
    }

    @PostMapping
    public Cuenta registerCuenta(@RequestBody Cuenta cuenta) {
        return this.cuentaService.saveOrUpdateCuenta(cuenta);
    }

    @PutMapping
    public Cuenta updateCuenta(@RequestBody Cuenta cuenta) {
        return this.cuentaService.saveOrUpdateCuenta(cuenta);
    }

    @DeleteMapping(path = "{cuentaId}")
    public ResponseEntity<Object> deleteCuenta(@PathVariable("cuentaId") Long id) {
        return this.cuentaService.deleteCuenta(id);
    }
}
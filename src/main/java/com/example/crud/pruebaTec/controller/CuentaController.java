package com.example.crud.pruebaTec.controller;

import com.example.crud.pruebaTec.dto.CuentaDto;
import com.example.crud.pruebaTec.service.CuentaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;

    @Autowired
    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @GetMapping
    public ResponseEntity<List<CuentaDto>> getCuentas() {
        return ResponseEntity.ok(cuentaService.getCuentas());
    }

    @PostMapping
    public ResponseEntity<CuentaDto> createCuenta(@Valid @RequestBody CuentaDto cuentaDto) {
        return ResponseEntity.ok(cuentaService.createCuenta(cuentaDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuentaDto> updateCuenta(@PathVariable Long id,
                                                  @Valid @RequestBody CuentaDto cuentaDto) {
        return ResponseEntity.ok(cuentaService.updateCuenta(id, cuentaDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuenta(@PathVariable Long id) {
        cuentaService.deleteCuenta(id);
        return ResponseEntity.noContent().build();
    }
}

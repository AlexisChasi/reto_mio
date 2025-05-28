package com.example.crud.pruebaTec.controller;

import com.example.crud.pruebaTec.dto.CuentaDto;
import com.example.crud.pruebaTec.service.CuentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/cuentas")
@Tag(name = "Cuentas", description = "Operaciones sobre cuentas bancarias")
public class CuentaController {

    private final CuentaService cuentaService;

    @Autowired
    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @Operation(summary = "Obtener todas las cuentas", description = "Devuelve una lista de todas las cuentas registradas")
    @GetMapping
    public ResponseEntity<List<CuentaDto>> getCuentas() {
        return ResponseEntity.ok(cuentaService.getCuentas());
    }

    @Operation(summary = "Crear cuenta", description = "Crea una nueva cuenta bancaria asociada a un cliente")
    @PostMapping
    public ResponseEntity<CuentaDto> createCuenta(@Valid @RequestBody CuentaDto cuentaDto) {
        return ResponseEntity.ok(cuentaService.createCuenta(cuentaDto));
    }

    @Operation(summary = "Actualizar cuenta", description = "Actualiza una cuenta bancaria existente por ID")
    @PutMapping("/{id}")
    public ResponseEntity<CuentaDto> updateCuenta(@PathVariable Long id,
                                                  @Valid @RequestBody CuentaDto cuentaDto) {
        return ResponseEntity.ok(cuentaService.updateCuenta(id, cuentaDto));
    }

    @Operation(summary = "Eliminar cuenta", description = "Elimina una cuenta existente por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuenta(@PathVariable Long id) {
        cuentaService.deleteCuenta(id);
        return ResponseEntity.noContent().build();
    }
}

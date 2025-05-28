package com.example.crud.pruebaTec.controller;

import com.example.crud.pruebaTec.dto.MovimientoDto;
import com.example.crud.pruebaTec.dto.ReporteDto;
import com.example.crud.pruebaTec.model.Movimiento;
import com.example.crud.pruebaTec.service.MovimientosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/movimientos")
@Tag(name = "Movimientos", description = "Operaciones sobre movimientos bancarios")
public class MovimientosController {

    private final MovimientosService movimientosService;

    @Autowired
    public MovimientosController(MovimientosService movimientosService) {
        this.movimientosService = movimientosService;
    }

    @Operation(summary = "Obtener todos los movimientos", description = "Devuelve una lista de todos los movimientos registrados")
    @GetMapping
    public ResponseEntity<List<Movimiento>> getMovimientos() {
        return ResponseEntity.ok(movimientosService.getMovimientos());
    }

    @Operation(summary = "Registrar nuevo movimiento", description = "Crea un movimiento tipo depósito o retiro sobre una cuenta")
    @PostMapping
    public ResponseEntity<?> registerMovimiento(@Valid @RequestBody MovimientoDto dto) {
        try {
            Movimiento nuevoMovimiento = movimientosService.crearMovimiento(dto);
            return ResponseEntity.status(201).body(nuevoMovimiento);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Actualizar movimiento (No permitido)", description = "El sistema no permite modificar un movimiento una vez creado")
    @PutMapping
    public ResponseEntity<?> updateMovimiento() {
        return ResponseEntity.status(405).body("No se permite modificar un movimiento existente");
    }

    @Operation(summary = "Eliminar movimiento", description = "Elimina un movimiento existente por su ID")
    @DeleteMapping("/{movimientoId}")
    public ResponseEntity<String> deleteMovimiento(@PathVariable Long movimientoId) {
        movimientosService.deleteMovimiento(movimientoId);
        return ResponseEntity.ok("Movimiento eliminado correctamente");
    }

    @Operation(summary = "Generar reporte de movimientos", description = "Devuelve un reporte filtrado por cliente y rango de fechas")
    @GetMapping("/reportes")
    public ResponseEntity<List<ReporteDto>> getReporte(
            @RequestParam Long clienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta
    ) {
        List<ReporteDto> reporte = movimientosService.getReportePorClienteYFechas(clienteId, fechaDesde, fechaHasta);
        return ResponseEntity.ok(reporte);
    }

    @Operation(summary = "Buscar movimientos por tipo", description = "Obtiene una lista de movimientos filtrados por tipo (Depósito o Retiro)")
    @GetMapping("/tipo")
    public ResponseEntity<List<Movimiento>> getByTipoMovimiento(@RequestParam String tipo) {
        List<Movimiento> movimientos = movimientosService.getMovimientosPorTipo(tipo);
        return ResponseEntity.ok(movimientos);
    }


}

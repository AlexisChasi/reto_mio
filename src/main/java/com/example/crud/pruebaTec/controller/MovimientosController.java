package com.example.crud.pruebaTec.controller;

import com.example.crud.pruebaTec.dto.MovimientoDto;
import com.example.crud.pruebaTec.dto.ReporteDto;
import com.example.crud.pruebaTec.model.Movimiento;
import com.example.crud.pruebaTec.service.MovimientosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/movimientos")
public class MovimientosController {

    private final MovimientosService movimientosService;

    @Autowired
    public MovimientosController(MovimientosService movimientosService) {
        this.movimientosService = movimientosService;
    }

    @GetMapping
    public ResponseEntity<List<Movimiento>> getMovimientos() {
        return ResponseEntity.ok(movimientosService.getMovimientos());
    }

    @PostMapping
    public ResponseEntity<?> registerMovimiento(@Valid @RequestBody MovimientoDto dto) {
        try {
            Movimiento nuevoMovimiento = movimientosService.crearMovimiento(dto);
            return ResponseEntity.status(201).body(nuevoMovimiento);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> updateMovimiento() {
        return ResponseEntity.status(405).body("No se permite modificar un movimiento existente");
    }

    @DeleteMapping("/{movimientoId}")
    public ResponseEntity<String> deleteMovimiento(@PathVariable Long movimientoId) {
        movimientosService.deleteMovimiento(movimientoId);
        return ResponseEntity.ok("Movimiento eliminado correctamente");
    }

    @GetMapping("/reportes")
    public ResponseEntity<List<ReporteDto>> getReporte(
            @RequestParam Long clienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta
    ) {
        List<ReporteDto> reporte = movimientosService.getReportePorClienteYFechas(clienteId, fechaDesde, fechaHasta);
        return ResponseEntity.ok(reporte);
    }
    @GetMapping("/tipo")
    public ResponseEntity<List<Movimiento>> getByTipoMovimiento(@RequestParam String tipo) {
        List<Movimiento> movimientos = movimientosService.getMovimientosPorTipo(tipo);
        return ResponseEntity.ok(movimientos);
    }


}

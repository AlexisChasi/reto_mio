package com.example.crud.pruebaTec.controller;

import com.example.crud.pruebaTec.model.Movimiento;
import com.example.crud.pruebaTec.service.MovimientosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/movimientos")
public class MovimientosController {

    private final MovimientosService movimientosService;

    @Autowired
    public MovimientosController(MovimientosService movimientosService) {
        this.movimientosService = movimientosService;
    }

    @GetMapping
    public List<Movimiento> getMovimientos() {
        return movimientosService.getMovimientos();
    }

    @PostMapping
    public ResponseEntity<Object> registerMovimiento(@RequestBody Movimiento movimiento) {
        return (ResponseEntity<Object>) movimientosService.newMovimiento(movimiento);
    }

    @PutMapping
    public ResponseEntity<Object> updateMovimiento(@RequestBody Movimiento movimiento) {
        return (ResponseEntity<Object>) movimientosService.newMovimiento(movimiento);
    }

    @DeleteMapping(path = "{movimientoId}")
    public ResponseEntity<Object> deleteMovimiento(@PathVariable("movimientoId") Long id) {
        return movimientosService.deleteMovimiento(id);
    }
}

package com.example.crud.pruebaTec.service;

import com.example.crud.pruebaTec.model.Movimiento;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface MovimientosService {
    List<Movimiento> getMovimientos();
    ResponseEntity<?> newMovimiento(Movimiento movimiento);
    ResponseEntity<Object> deleteMovimiento(Long id);
    List<Movimiento> getMovimientosByTipoMovimiento(String tipoMovimiento);
    List<Movimiento> getMovimientosPorClienteYFecha(String clienteid, LocalDate fechaInicio, LocalDate fechaFin);
}

package com.example.crud.pruebaTec.service;

import com.example.crud.pruebaTec.dto.MovimientoDto;
import com.example.crud.pruebaTec.dto.ReporteDto;
import com.example.crud.pruebaTec.model.Movimiento;

import java.time.LocalDate;
import java.util.List;

public interface MovimientosService {
    List<Movimiento> getMovimientos();
    Movimiento crearMovimiento(MovimientoDto dto);
    void deleteMovimiento(Long id);
    List<ReporteDto> getReportePorClienteYFechas(Long clienteId, LocalDate desde, LocalDate hasta);
    List<Movimiento> getMovimientosPorTipo(String tipoMovimiento);



}

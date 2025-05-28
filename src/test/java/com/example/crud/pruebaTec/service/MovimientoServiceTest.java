package com.example.crud.pruebaTec.service;

import com.example.crud.pruebaTec.dto.MovimientoDto;
import com.example.crud.pruebaTec.dto.ReporteDto;
import com.example.crud.pruebaTec.exeption.ResourceNotFoundException;
import com.example.crud.pruebaTec.model.Cuenta;
import com.example.crud.pruebaTec.model.Movimiento;
import com.example.crud.pruebaTec.model.Cliente;
import com.example.crud.pruebaTec.repository.CuentaRepository;
import com.example.crud.pruebaTec.repository.MovimientosRepository;
import com.example.crud.pruebaTec.service.serviceImpl.MovimientosServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MovimientoServiceTest {

    private MovimientosRepository movimientosRepository;
    private CuentaRepository cuentaRepository;
    private MovimientosService movimientosService;

    @BeforeEach
    void setUp() {
        movimientosRepository = mock(MovimientosRepository.class);
        cuentaRepository = mock(CuentaRepository.class);
        movimientosService = new MovimientosServiceImpl(movimientosRepository, cuentaRepository);
    }

    @Test
    void registrarDepositoExitosamente() {
        MovimientoDto dto = MovimientoDto.builder()
                .numeroCuenta("12345678")
                .tipoMovimiento("DEPOSITO")
                .valor(200)
                .build();

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Juan Pérez");

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta("12345678");
        cuenta.setSaldoInicial(1000);
        cuenta.setCliente(cliente);

        when(cuentaRepository.findByNumeroCuenta("12345678")).thenReturn(Optional.of(cuenta));
        when(movimientosRepository.save(any(Movimiento.class))).thenAnswer(inv -> {
            Movimiento m = inv.getArgument(0);
            m.setId(1L);
            return m;
        });

        Movimiento result = movimientosService.crearMovimiento(dto);

        assertNotNull(result);
        assertEquals("DEPOSITO", result.getTipoMovimiento());
        assertEquals(200, result.getValor());
        assertEquals(1200, result.getSaldo());
        verify(cuentaRepository).save(any(Cuenta.class));
        verify(movimientosRepository).save(any(Movimiento.class));
    }

    @Test
    void registrarRetiroExitosamente() {
        MovimientoDto dto = MovimientoDto.builder()
                .numeroCuenta("87654321")
                .tipoMovimiento("RETIRO")
                .valor(300)
                .build();

        Cliente cliente = new Cliente();
        cliente.setId(2L);
        cliente.setNombre("Ana Torres");

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta("87654321");
        cuenta.setSaldoInicial(1000);
        cuenta.setCliente(cliente);

        when(cuentaRepository.findByNumeroCuenta("87654321")).thenReturn(Optional.of(cuenta));
        when(movimientosRepository.save(any(Movimiento.class))).thenAnswer(inv -> {
            Movimiento m = inv.getArgument(0);
            m.setId(2L);
            return m;
        });

        Movimiento result = movimientosService.crearMovimiento(dto);

        assertNotNull(result);
        assertEquals("RETIRO", result.getTipoMovimiento());
        assertEquals(300, result.getValor());
        assertEquals(700, result.getSaldo());
    }

    @Test
    void registrarRetiroConSaldoInsuficiente() {
        MovimientoDto dto = MovimientoDto.builder()
                .numeroCuenta("55555555")
                .tipoMovimiento("RETIRO")
                .valor(1000)
                .build();

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta("55555555");
        cuenta.setSaldoInicial(500);

        when(cuentaRepository.findByNumeroCuenta("55555555")).thenReturn(Optional.of(cuenta));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> movimientosService.crearMovimiento(dto));

        assertEquals("Saldo no disponible", exception.getMessage());
    }

    @Test
    void eliminarMovimientoExitosamente() {
        when(movimientosRepository.existsById(1L)).thenReturn(true);

        movimientosService.deleteMovimiento(1L);

        verify(movimientosRepository).deleteById(1L);
    }

    @Test
    void eliminarMovimientoInexistente() {
        when(movimientosRepository.existsById(999L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> movimientosService.deleteMovimiento(999L));
    }

    @Test
    void obtenerMovimientosPorTipo() {
        Movimiento m1 = new Movimiento();
        m1.setTipoMovimiento("DEPOSITO");
        m1.setValor(300);

        Movimiento m2 = new Movimiento();
        m2.setTipoMovimiento("DEPOSITO");
        m2.setValor(500);

        when(movimientosRepository.findByTipoMovimiento("DEPOSITO"))
                .thenReturn(List.of(m1, m2));

        List<Movimiento> result = movimientosService.getMovimientosPorTipo("DEPOSITO");

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(m -> m.getTipoMovimiento().equalsIgnoreCase("DEPOSITO")));
    }

    @Test
    void generarReportePorClienteYFechas() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Cliente Test");

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta("88888888");
        cuenta.setTipoCuenta("Ahorro");
        cuenta.setSaldoInicial(1000);
        cuenta.setEstado(true);
        cuenta.setCliente(cliente);

        Movimiento m1 = new Movimiento();
        m1.setFecha(LocalDate.of(2024, 5, 1));
        m1.setCuenta(cuenta);
        m1.setValor(200);
        m1.setSaldo(1200);

        Movimiento m2 = new Movimiento();
        m2.setFecha(LocalDate.of(2024, 5, 5));
        m2.setCuenta(cuenta);
        m2.setValor(100);
        m2.setSaldo(1100);

        when(movimientosRepository.findByClienteIdAndFechaBetween(1L,
                LocalDate.of(2024, 5, 1),
                LocalDate.of(2024, 5, 31)))
                .thenReturn(List.of(m1, m2));

        List<ReporteDto> reporte = movimientosService.getReportePorClienteYFechas(
                1L, LocalDate.of(2024, 5, 1), LocalDate.of(2024, 5, 31)
        );

        assertEquals(2, reporte.size());
        assertEquals("Cliente Test", reporte.get(0).getCliente());
    }
}

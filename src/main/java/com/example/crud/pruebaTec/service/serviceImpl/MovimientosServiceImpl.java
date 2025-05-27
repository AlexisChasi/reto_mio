package com.example.crud.pruebaTec.service.serviceImpl;

import com.example.crud.pruebaTec.dto.MovimientoDto;
import com.example.crud.pruebaTec.dto.ReporteDto;
import com.example.crud.pruebaTec.exeption.ResourceNotFoundException;
import com.example.crud.pruebaTec.mapper.MovimientoMapper;
import com.example.crud.pruebaTec.model.Cuenta;
import com.example.crud.pruebaTec.model.Movimiento;
import com.example.crud.pruebaTec.repository.CuentaRepository;
import com.example.crud.pruebaTec.repository.MovimientosRepository;
import com.example.crud.pruebaTec.service.MovimientosService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
public class MovimientosServiceImpl implements MovimientosService {

    private final MovimientosRepository movimientosRepository;
    private final CuentaRepository cuentaRepository;

    @Autowired
    public MovimientosServiceImpl(MovimientosRepository movimientosRepository, CuentaRepository cuentaRepository) {
        this.movimientosRepository = movimientosRepository;
        this.cuentaRepository = cuentaRepository;
    }

    @Override
    public List<Movimiento> getMovimientos() {
        log.info("Consultando todos los movimientos");
        return movimientosRepository.findAll();
    }

    @Override
    public Movimiento crearMovimiento(MovimientoDto dto) {
        log.info("Iniciando creación de movimiento para cuenta {}", dto.getNumeroCuenta());

        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(dto.getNumeroCuenta())
                .orElseThrow(() -> {
                    log.warn("Cuenta no encontrada: {}", dto.getNumeroCuenta());
                    return new ResourceNotFoundException("Cuenta no encontrada");
                });

        double saldoActual = cuenta.getSaldoInicial();
        double nuevoSaldo;
        String tipo = dto.getTipoMovimiento().toUpperCase();
        double valor = dto.getValor();

        if (!tipo.equals("RETIRO") && !tipo.equals("DEPOSITO")) {
            log.warn("Tipo de movimiento inválido: {}", tipo);
            throw new IllegalArgumentException("Tipo de movimiento inválido");
        }

        if (valor <= 0) {
            log.warn("El valor del movimiento debe ser mayor a cero: {}", valor);
            throw new IllegalArgumentException("El valor debe ser mayor que cero");
        }

        if (tipo.equals("RETIRO")) {
            if (valor > saldoActual) {
                log.warn("Saldo insuficiente. Saldo actual: {}, intento de retiro: {}", saldoActual, valor);
                throw new IllegalArgumentException("Saldo no disponible");
            }
            nuevoSaldo = saldoActual - valor;
        } else {
            nuevoSaldo = saldoActual + valor;
        }

        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepository.save(cuenta);
        log.info("Cuenta actualizada. Nuevo saldo: {}", nuevoSaldo);

        Movimiento movimiento = MovimientoMapper.toMovimiento(dto, cuenta);
        movimiento.setFecha(LocalDate.now());
        movimiento.setSaldo(nuevoSaldo);

        Movimiento guardado = movimientosRepository.save(movimiento);
        log.info("Movimiento guardado con éxito. ID: {}", guardado.getId());

        return guardado;
    }

    @Override
    public void deleteMovimiento(Long id) {
        if (!movimientosRepository.existsById(id)) {
            log.warn("Intento de eliminar movimiento inexistente. ID: {}", id);
            throw new ResourceNotFoundException("Movimiento no encontrado");
        }
        movimientosRepository.deleteById(id);
        log.info("Movimiento eliminado exitosamente. ID: {}", id);
    }

    @Override
    public List<Movimiento> getMovimientosPorTipo(String tipoMovimiento) {
        log.info("Buscando movimientos por tipo: {}", tipoMovimiento);
        return movimientosRepository.findByTipoMovimiento(tipoMovimiento.toUpperCase());
    }

    @Override
    public List<ReporteDto> getReportePorClienteYFechas(Long clienteId, LocalDate desde, LocalDate hasta) {
        log.info("Generando reporte de movimientos. Cliente ID: {}, Desde: {}, Hasta: {}", clienteId, desde, hasta);

        List<Movimiento> movimientos = movimientosRepository.findByClienteIdAndFechaBetween(clienteId, desde, hasta);

        log.info("Total movimientos encontrados: {}", movimientos.size());

        return movimientos.stream().map(m -> ReporteDto.builder()
                .fecha(m.getFecha())
                .cliente(m.getCuenta().getCliente().getNombre())
                .numeroCuenta(m.getCuenta().getNumeroCuenta())
                .tipo(m.getCuenta().getTipoCuenta())
                .saldoInicial(m.getCuenta().getSaldoInicial())
                .estado(m.getCuenta().isEstado())
                .movimiento(m.getValor())
                .saldoDisponible(m.getSaldo())
                .build()
        ).collect(Collectors.toList());
    }
}

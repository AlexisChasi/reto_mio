package com.example.crud.pruebaTec.service.serviceImpl;

import com.example.crud.pruebaTec.exeption.ResourceNotFoundException;
import com.example.crud.pruebaTec.model.Cuenta;
import com.example.crud.pruebaTec.model.Movimiento;
import com.example.crud.pruebaTec.repository.CuentaRepository;
import com.example.crud.pruebaTec.repository.MovimientosRepository;

import com.example.crud.pruebaTec.service.MovimientosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class MovimientosServiceImpl implements MovimientosService {

    private final MovimientosRepository movimientosRepository;
    private final CuentaRepository cuentaRepository;
    private HashMap<String, Object> dataMovimiento;

    @Autowired
    public MovimientosServiceImpl(MovimientosRepository movimientosRepository, CuentaRepository cuentaRepository) {
        this.movimientosRepository = movimientosRepository;
        this.cuentaRepository = cuentaRepository;
    }

    @Override
    public List<Movimiento> getMovimientos() {
        return movimientosRepository.findAll();
    }

    @Override
    public ResponseEntity<?> newMovimiento(Movimiento movimiento) {
        Optional<Cuenta> cuentaOpt = cuentaRepository.findByNumeroCuenta(movimiento.getCuenta().getNumeroCuenta());
        if (!cuentaOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cuenta no encontrada");
        }

        Cuenta cuenta = cuentaOpt.get();
        double saldoActual = cuenta.getSaldoInicial();
        double nuevoSaldo;

        String tipo = movimiento.getTipoMovimiento().toUpperCase();

        if (tipo.equals("RETIRO")) {
            if (movimiento.getValor() > saldoActual) {
                return ResponseEntity.badRequest().body("Saldo insuficiente");
            }
            nuevoSaldo = saldoActual - movimiento.getValor();
        } else if (tipo.equals("DEPOSITO")) {
            nuevoSaldo = saldoActual + movimiento.getValor();
        } else {
            return ResponseEntity.badRequest().body("Tipo de movimiento inválido");
        }

        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepository.save(cuenta);

        movimiento.setFecha(LocalDate.now());
        movimiento.setSaldo(nuevoSaldo);
        movimiento.setCuenta(cuenta);
        movimientosRepository.save(movimiento);

        return ResponseEntity.ok(movimiento);
    }

    @Override
    public ResponseEntity<Object> deleteMovimiento(Long id) {
        dataMovimiento = new HashMap<>();
        if (!movimientosRepository.existsById(id)) {
            throw new ResourceNotFoundException("Movimiento no encontrado");
        }
        movimientosRepository.deleteById(id);
        dataMovimiento.put("message", "Movimiento eliminado");
        return ResponseEntity.ok(dataMovimiento);
    }

    @Override
    public List<Movimiento> getMovimientosByTipoMovimiento(String tipoMovimiento) {
        return movimientosRepository.findByTipoMovimiento(tipoMovimiento);
    }

    @Override
    public List<Movimiento> getMovimientosPorClienteYFecha(String clienteid, LocalDate fechaInicio, LocalDate fechaFin) {
        return movimientosRepository.findByCuentaClienteClienteidAndFechaBetween(clienteid, fechaInicio, fechaFin);
    }
}

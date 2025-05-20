package com.example.crud.pruebaTec.repository;


import com.example.crud.pruebaTec.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface MovimientosRepository extends JpaRepository<Movimiento, Long> {
    // Método para obtener los movimientos por tipo de movimiento (Corriente o Ahorro)
    List<Movimiento> findByTipoMovimiento(String tipoMovimiento);

    List<Movimiento> findByCuentaClienteClienteidAndFechaBetween(String clienteid, LocalDate startDate, LocalDate endDate);

}

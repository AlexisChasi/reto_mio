package com.example.crud.pruebaTec.repository;

import com.example.crud.pruebaTec.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovimientosRepository extends JpaRepository<Movimiento, Long> {
    @Query("SELECT m FROM Movimiento m WHERE UPPER(m.tipoMovimiento) = UPPER(:tipo)")
    List<Movimiento> findByTipoMovimiento(@Param("tipo") String tipo);

    @Query("SELECT m FROM Movimiento m WHERE m.cuenta.cliente.id = :clienteId AND m.fecha BETWEEN :desde AND :hasta")
    List<Movimiento> findByClienteIdAndFechaBetween(@Param("clienteId") Long clienteId,
                                                    @Param("desde") LocalDate desde,
                                                    @Param("hasta") LocalDate hasta);

}

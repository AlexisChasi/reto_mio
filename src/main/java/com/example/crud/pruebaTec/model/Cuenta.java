package com.example.crud.pruebaTec.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cuentas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String numeroCuenta;

    private String tipoCuenta;
    private double saldoInicial;
    private boolean estado;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}

package com.example.crud.pruebaTec.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "personas")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Persona implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String genero;
    private int edad;

    @Column(unique = true)
    private String identificacion;

    private String direccion;
    private String telefono;
}

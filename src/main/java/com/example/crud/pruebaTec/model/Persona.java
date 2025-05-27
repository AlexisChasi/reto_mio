package com.example.crud.pruebaTec.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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

    @Column(nullable = false)
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    private String genero;

    @Min(value = 0, message = "La edad debe ser positiva")
    private int edad;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "La identificación no puede estar vacía")
    private String identificacion;

    @Column(nullable = false)
    @NotBlank(message = "La dirección no puede estar vacía")
    private String direccion;

    @Column(nullable = false)
    @NotBlank(message = "El teléfono no puede estar vacío")
    private String telefono;
}

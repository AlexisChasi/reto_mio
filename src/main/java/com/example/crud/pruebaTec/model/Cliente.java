package com.example.crud.pruebaTec.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente extends Persona {

    @Column(unique = true, nullable = false)
    @NotBlank(message = "El clienteId no puede estar vacío")
    private String clienteid;

    @Column(nullable = false)
    @NotBlank(message = "La contraseña no puede estar vacía")
    private String contrasena;

    @Column(nullable = false)
    private boolean estado;
}

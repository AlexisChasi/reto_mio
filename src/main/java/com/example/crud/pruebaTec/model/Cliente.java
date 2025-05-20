package com.example.crud.pruebaTec.model;
import jakarta.persistence.*;
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

    @Column(unique = true)
    private String clienteid;
    private String contrasena;
    private boolean estado;
}
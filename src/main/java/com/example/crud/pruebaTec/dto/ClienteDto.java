package com.example.crud.pruebaTec.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClienteDto {

    private Long id;

    @NotBlank(message = "El clienteId no puede estar vacío")
    @Pattern(regexp = "^[a-zA-Z0-9]{5,20}$", message = "El clienteId debe ser alfanumérico entre 5 y 20 caracteres")
    private String clienteid;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]{3,50}$", message = "El nombre solo puede contener letras y espacios (mínimo 3 caracteres)")
    private String nombre;

    @NotBlank(message = "La identificación no puede estar vacía")
    @Pattern(regexp = "^\\d{10}$", message = "La identificación debe tener exactamente 10 dígitos")
    private String identificacion;

    @NotBlank(message = "La dirección no puede estar vacía")
    @Size(min = 5, max = 100, message = "La dirección debe tener entre 5 y 100 caracteres")
    private String direccion;

    @NotBlank(message = "El teléfono no puede estar vacío")
    @Pattern(regexp = "^\\d{10}$", message = "El teléfono debe tener exactamente 10 dígitos")
    private String telefono;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "La contraseña debe tener mínimo 8 caracteres, incluyendo letras y números"
    )
    private String contrasena;

    private boolean estado;
}

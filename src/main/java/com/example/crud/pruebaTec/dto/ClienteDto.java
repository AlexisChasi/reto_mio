package com.example.crud.pruebaTec.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClienteDto {

    private Long id;

    @NotBlank(message = "El clienteId no puede estar vacío")
    @Size(min = 5, max = 20, message = "El clienteId debe tener entre 5 y 20 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9]{5,20}$", message = "El clienteId debe ser alfanumérico sin caracteres especiales")
    private String clienteid;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Pattern(
            regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ]+(\\s[A-Za-zÁÉÍÓÚáéíóúÑñ]+)*$",
            message = "El nombre solo puede contener letras y un espacio entre palabras, sin espacios múltiples ni al inicio o final"
    )
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String nombre;

    @NotBlank(message = "La identificación no puede estar vacía")
    @Pattern(regexp = "^\\d{10}$", message = "La identificación debe tener exactamente 10 dígitos")
    private String identificacion;

    @NotBlank(message = "La dirección no puede estar vacía")
    @Size(min = 5, max = 100, message = "La dirección debe tener entre 5 y 100 caracteres")
    @Pattern(
            regexp = "^[A-Za-z0-9ÁÉÍÓÚáéíóúÑñ,.\\-\\s#]{5,100}$",
            message = "La dirección contiene caracteres inválidos"
    )
    private String direccion;

    @NotBlank(message = "El teléfono no puede estar vacío")
    @Pattern(regexp = "^\\d{10}$", message = "El teléfono debe tener exactamente 10 dígitos")
    private String telefono;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "La contraseña debe tener al menos 8 caracteres incluyendo mayúsculas, minúsculas, números y un carácter especial"
    )
    private String contrasena;

    private boolean estado;
}

package com.example.crud.pruebaTec.dto;

import com.example.crud.pruebaTec.validation.OnCreate;
import com.example.crud.pruebaTec.validation.OnUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClienteDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, example = "1", description = "ID del cliente (solo lectura)")
    private Long id;

    @NotBlank(message = "El clienteId no puede estar vacío", groups = OnCreate.class)
    @Size(min = 5, max = 20, message = "El clienteId debe tener entre 5 y 20 caracteres", groups = OnCreate.class)
    @Pattern(regexp = "^[a-zA-Z0-9]{5,20}$", message = "El clienteId debe ser alfanumérico sin caracteres especiales", groups = OnCreate.class)
    @Null(message = "El clienteId no debe ser enviado en actualización", groups = OnUpdate.class)
    private String clienteid;

    @NotBlank(message = "El nombre no puede estar vacío", groups = {OnCreate.class, OnUpdate.class})
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres", groups = {OnCreate.class, OnUpdate.class})
    @Pattern(
            regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ]+(\\s[A-Za-zÁÉÍÓÚáéíóúÑñ]+)*$",
            message = "El nombre solo puede contener letras y espacios",
            groups = {OnCreate.class, OnUpdate.class})
    private String nombre;

    @NotBlank(message = "La identificación no puede estar vacía", groups = OnCreate.class)
    @Pattern(regexp = "^\\d{10}$", message = "La identificación debe tener exactamente 10 dígitos", groups = OnCreate.class)
    @Null(message = "La identificación no debe ser enviada en actualización", groups = OnUpdate.class)
    private String identificacion;

    @NotBlank(message = "La dirección no puede estar vacía", groups = {OnCreate.class, OnUpdate.class})
    @Size(min = 5, max = 100, message = "La dirección debe tener entre 5 y 100 caracteres", groups = {OnCreate.class, OnUpdate.class})
    @Pattern(
            regexp = "^[A-Za-z0-9ÁÉÍÓÚáéíóúÑñ,.\\-\\s#]{5,100}$",
            message = "La dirección contiene caracteres inválidos",
            groups = {OnCreate.class, OnUpdate.class})
    private String direccion;

    @NotBlank(message = "El teléfono no puede estar vacío", groups = {OnCreate.class, OnUpdate.class})
    @Pattern(regexp = "^\\d{10}$", message = "El teléfono debe tener exactamente 10 dígitos", groups = {OnCreate.class, OnUpdate.class})
    private String telefono;

    @NotBlank(message = "La contraseña no puede estar vacía", groups = {OnCreate.class, OnUpdate.class})
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Debe tener al menos 8 caracteres, mayúsculas, minúsculas, números y un carácter especial",
            groups = {OnCreate.class, OnUpdate.class})
    private String contrasena;

    private boolean estado;

    @Min(value = 0, message = "La edad debe ser positiva", groups = OnCreate.class)
    @Max(value = 120, message = "La edad debe ser menor o igual a 120", groups = OnCreate.class)
    private int edad;

    @Pattern(
            regexp = "^(Masculino|Femenino)$",
            message = "El género debe ser Masculino o Femenino",
            groups = OnCreate.class)
    private String genero;
}

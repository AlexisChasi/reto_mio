package com.example.crud.pruebaTec.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoDto {

    @NotBlank(message = "Tipo de movimiento es obligatorio")
    @Pattern(
            regexp = "^(?i)(RETIRO|DEPOSITO)$",
            message = "El tipo de movimiento debe ser 'RETIRO' o 'DEPOSITO'"
    )
    private String tipoMovimiento;

    @Min(value = 1, message = "El valor debe ser mayor a cero")
    @Digits(integer = 10, fraction = 2, message = "El valor del movimiento puede tener hasta 2 decimales")
    private double valor;

    @NotBlank(message = "El número de cuenta es obligatorio")
    @Pattern(
            regexp = "^\\d{6,12}$",
            message = "El número de cuenta debe tener entre 6 y 12 dígitos"
    )
    private String numeroCuenta;
}

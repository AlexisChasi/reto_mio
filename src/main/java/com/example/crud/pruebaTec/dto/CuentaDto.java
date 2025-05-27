package com.example.crud.pruebaTec.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
public class CuentaDto {

    private Long id;

    @NotBlank(message = "Número de cuenta no puede estar vacío")
    @Pattern(regexp = "^\\d{6,12}$", message = "El número de cuenta debe tener entre 6 y 12 dígitos")
    private String numeroCuenta;

    @NotBlank(message = "Tipo de cuenta es obligatorio")
    @Pattern(regexp = "^(Ahorro|Corriente)$", message = "Tipo de cuenta debe ser 'Ahorro' o 'Corriente'")
    private String tipoCuenta;

    @Min(value = 0, message = "El saldo debe ser cero o positivo")
    private double saldoInicial;

    private boolean estado;

    @NotNull(message = "Debe indicar el clienteId asociado")
    private Long clienteId;
}

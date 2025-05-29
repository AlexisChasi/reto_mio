package com.example.crud.pruebaTec.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CuentaDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, example = "1", description = "ID único de la cuenta (solo lectura)")
    private Long id;

    @NotBlank(message = "Número de cuenta no puede estar vacío")
    @Pattern(
            regexp = "^\\d{6,12}$",
            message = "El número de cuenta debe tener entre 6 y 12 dígitos"
    )
    @Schema(example = "958978", description = "Número único de la cuenta (entre 6 y 12 dígitos)")
    private String numeroCuenta;

    @NotBlank(message = "Tipo de cuenta es obligatorio")
    @Pattern(
            regexp = "^(?i)(Ahorro|Corriente)$",
            message = "Tipo de cuenta debe ser 'Ahorro' o 'Corriente'"
    )
    @Schema(example = "Ahorro", description = "Tipo de cuenta: 'Ahorro' o 'Corriente'")
    private String tipoCuenta;

    @Min(value = 0, message = "El saldo debe ser cero o positivo")
    @Digits(integer = 10, fraction = 2, message = "El saldo puede tener hasta 2 decimales")
    @Schema(example = "600.00", description = "Saldo inicial de la cuenta (máx. 2 decimales)")
    private double saldoInicial;

    @Schema(example = "true", description = "Indica si la cuenta está activa")
    private boolean estado;

    @NotNull(message = "Debe indicar el clienteId asociado")
    @Schema(example = "1", description = "ID del cliente asociado a esta cuenta")
    private Long clienteId;
}

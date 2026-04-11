package com.acm.tiendaerick.paqueteClientes.dtoCliente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record PagoRealizadoDTO(
        @NotBlank(message = "El id del cliente no puede estar vacío")
        Long id_cliente,

        @PositiveOrZero(message = "El saldo actual no puede ser negativo")
        Long saldo_actual,

        @NotBlank(message = "El mensaje de confirmación no puede estar vacío")
        String mensaje
)
{}

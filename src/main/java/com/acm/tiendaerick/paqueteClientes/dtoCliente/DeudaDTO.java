package com.acm.tiendaerick.paqueteClientes.dtoCliente;

import jakarta.validation.constraints.NegativeOrZero;
import jakarta.validation.constraints.PositiveOrZero;

public record DeudaDTO(
        @PositiveOrZero(message = "El total no puede ser negativo")
        Long total,

        list<MontoDTO> movimientos) {
}

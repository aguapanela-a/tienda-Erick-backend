package com.acm.tiendaerick.paqueteClientes.dtoCliente;

import com.acm.tiendaerick.dtoCompartido.MontoDTO;
import jakarta.validation.constraints.NegativeOrZero;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

public record DeudaDTO(
        @PositiveOrZero(message = "El total no puede ser negativo")
        Long total,

        List<MontoDTO> movimientos) {
}

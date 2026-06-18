package com.acm.tiendaerick.paqueteClientes.dtoCliente;

import java.math.BigDecimal;
import java.util.List;

import com.acm.tiendaerick.compartido.dtoCompartido.MontoDeClienteDTO;

import jakarta.validation.constraints.PositiveOrZero;

public record DeudaDTO(
        @PositiveOrZero(message = "El total no puede ser negativo")
        BigDecimal total,

        List<MontoDeClienteDTO> movimientos) {
}

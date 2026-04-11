package com.acm.tiendaerick.paqueteClientes.dtoCliente;

import com.acm.tiendaerick.paqueteClientes.tipoEnum.TipoCliente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record TipoClienteDTO(
        @PositiveOrZero(message = "El id del cliente no puede ser negativo")
        Long id_cliente,

        @NotNull(message = "El tipo de cliente no puede ser nulo")
        TipoCliente tipo_cliente
)
{}

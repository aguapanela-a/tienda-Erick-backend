package com.acm.tiendaerick.paqueteClientes.dtoCliente;

import com.acm.tiendaerick.paqueteClientes.tipoEnum.TipoCliente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClienteRegistroDTO(
        @NotBlank(message = "El nombre del cliente no puede estar vacío")
        String nombre,

        @NotNull(message = "El tipo de cliente no puede ser nulo")
        TipoCliente tipo_cliente)
{}

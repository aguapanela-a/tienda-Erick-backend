package com.acm.tiendaerick.paqueteClientes.dtoCliente;

import com.acm.tiendaerick.paqueteClientes.tipoEnum.TipoCliente;
import jakarta.validation.constraints.NotNull;

public record ClienteRegistroDTO(
        @NotNull
        String nombre,

        @NotNull
        TipoCliente tipo_cliente)
{}

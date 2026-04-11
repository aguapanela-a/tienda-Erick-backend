package com.acm.tiendaerick.paqueteClientes.dtoCliente;

import jakarta.validation.constraints.NotBlank;

public record TipoClienteDTO(
        @NotBlank(message = "El id del cliente no puede estar vacío")
        Long id_cliente,

        @NotBlank(message = "El tipo de cliente no puede estar vacío")
        String tipo_cliente
)
{}

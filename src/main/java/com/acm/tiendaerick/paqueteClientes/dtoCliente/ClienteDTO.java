package com.acm.tiendaerick.paqueteClientes.dtoCliente;

import com.acm.tiendaerick.paqueteClientes.tipoEnum.TipoCliente;
import jakarta.validation.constraints.NotBlank;

public record ClienteDTO(

        @NotBlank(message = "El ID del cliente no puede estar vacío")
        long id_cliente,

        @NotBlank(message = "El nombre del cliente no puede estar vacío")
        String nombre,

        @NotBlank(message = "El tipo de cliente no puede estar vacío")
        TipoCliente tipo_cliente)
{}

//toca poner anotaciòn @Valid en los argumentos del controlador

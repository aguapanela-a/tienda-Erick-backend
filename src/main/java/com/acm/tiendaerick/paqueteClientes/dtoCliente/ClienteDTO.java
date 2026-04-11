package com.acm.tiendaerick.paqueteClientes.dtoCliente;

import com.acm.tiendaerick.paqueteClientes.tipoEnum.TipoCliente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record ClienteDTO(

        @PositiveOrZero(message = "El ID del cliente no puede estar vacío")
        Long id_cliente,

        @NotBlank(message = "El nombre del cliente no puede estar vacío")
        String nombre,

        @NotNull(message = "El tipo de cliente  es obligatorio")
        TipoCliente tipo_cliente)
{}

//toca poner anotaciòn @Valid en los argumentos del controlador

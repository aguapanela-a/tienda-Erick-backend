package com.acm.tiendaerick.dtoCompartido;

import com.acm.tiendaerick.paqueteMontos.tipoEnum.TipoMonto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record MontoDeClienteDTO(
        @Positive(message = "El id del monto no puede estar vacío")
        Long id_monto,

        @Positive (message="El id del cliente no puede estar vacío")
        Long id_cliente,

        String descripcion,

        @Positive(message = "El valor del monto no puede estar vacío")
        BigDecimal valor,

        @NotBlank(message = "El tipo del monto no puede estar vacío")
        TipoMonto tipo_monto,

        @NotBlank(message = "La fecha del monto no puede estar vacía")
        String fecha
        )
{}

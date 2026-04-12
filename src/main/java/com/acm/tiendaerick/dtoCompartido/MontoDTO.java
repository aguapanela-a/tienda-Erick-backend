package com.acm.tiendaerick.dtoCompartido;

import com.acm.tiendaerick.paqueteMontos.tipoEnum.TipoMonto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record MontoDTO(
        @PositiveOrZero(message = "El id del monto no puede ser negativo")
        long id_monto,

        String descripcion,

        @NotNull(message = "El valor del monto no puede ser nulo")
        BigDecimal valor,

        @NotNull(message = "El tipo del monto no puede ser nulo")
        TipoMonto tipo_monto,

        @NotBlank(message = "La fecha del monto no puede estar vacía")
        String fecha
)
{}

package com.acm.tiendaerick.dtoCompartido;

import java.math.BigDecimal;

import com.acm.tiendaerick.paqueteMontos.tipoEnum.TipoMonto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record MontoDTO(
        @PositiveOrZero(message = "El id del cliente no puede ser negativo")
        long id_cliente,

        String descripcion,

        @NotNull(message = "El valor del monto no puede ser nulo")
        BigDecimal valor,

        @NotNull(message = "El tipo de monto es obligatorio y debe ser un valor válido (ABONO, DEUDA)")
        TipoMonto tipo_monto,

        @NotBlank(message = "La fecha del monto no puede estar vacía")
        String fecha
)
{}

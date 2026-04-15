package com.acm.tiendaerick.dtoCompartido;

import com.acm.tiendaerick.paqueteMontos.tipoEnum.TipoMonto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record MontoDeClienteDTO(
        @Positive(message = "El id del monto no puede estar vacío")
        long id_monto,

        @Positive (message="El id del cliente no puede estar vacío")
        long id_cliente,

        String descripcion,

        @Positive(message = "El valor del monto no puede estar vacío")
        BigDecimal valor,

        @NotNull(message = "El tipo de cliente es obligatorio y debe ser un valor válido (FRECUENTE, INVITADO)")
        TipoMonto tipo_monto,

        @NotBlank(message = "La fecha del monto no puede estar vacía")
        String fecha
        )
{}

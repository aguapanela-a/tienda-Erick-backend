package com.acm.tiendaerick.autenticacion.authDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginDTO(
        @NotNull(message = "El numero de teléfono no puede ser nulo")
        String numeroTelefono,

        @NotNull
        @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
        String password
) { }

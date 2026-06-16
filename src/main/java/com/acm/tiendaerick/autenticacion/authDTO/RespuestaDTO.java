package com.acm.tiendaerick.autenticacion.authDTO;

import java.util.UUID;

public record RespuestaDTO(
        String token,
        String nombre,
        UUID id
) {
}

package com.acm.tiendaerick.autenticacion.authDTO;

import java.util.UUID;

//TODO Tener en cuenta que este DTO cambió a la hora de modificar el frontend
public record RespuestaDTO(
        String accessToken,
        String refreshToken,
        String nombre,
        UUID id
) {
}

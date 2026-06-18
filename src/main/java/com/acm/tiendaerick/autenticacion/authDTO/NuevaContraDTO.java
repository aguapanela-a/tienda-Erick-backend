package com.acm.tiendaerick.autenticacion.authDTO;

public record NuevaContraDTO(
        String telefono,
        String contrasenaVieja,
        String contrasenaNueva
) {}

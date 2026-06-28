package com.acm.tiendaerick.autenticacion.controlador;

import com.acm.tiendaerick.autenticacion.authDTO.AccessTokenDTO;
import com.acm.tiendaerick.autenticacion.authDTO.LoginDTO;
import com.acm.tiendaerick.autenticacion.authDTO.RespuestaDTO;
import com.acm.tiendaerick.autenticacion.servicio.AutenticacionServicio;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.acm.tiendaerick.autenticacion.authDTO.NuevaContraDTO;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
public class ControladorAutenticacion {

    private AutenticacionServicio autenticacionServicio;

    public ControladorAutenticacion(AutenticacionServicio autenticacionServicio) {
        this.autenticacionServicio = autenticacionServicio;
    }

    @PostMapping("/login")
    public ResponseEntity<RespuestaDTO>  login(@Valid @RequestBody LoginDTO loginDTO) throws JOSEException {
        return  ResponseEntity.ok(autenticacionServicio.login(loginDTO));
    }

    @PutMapping("/cambiarContrasena")
    public ResponseEntity<String> cambiarContrasena(@Valid @RequestBody NuevaContraDTO dto){
        return ResponseEntity.ok(autenticacionServicio.cambiarContrasena(dto));
    }

    @PostMapping("/refrescarToken")
    public ResponseEntity<AccessTokenDTO> refrescarToken(@RequestParam String tokenRefresh) throws JOSEException, ParseException {
        return ResponseEntity.ok(autenticacionServicio.refrescarToken(tokenRefresh));
    }

}
// TODO: Implementar Token Refresh (Todo hecho - falta frontend)
//
// [ ] 1. Entidad User
//        → Agregar campo `String refreshToken`
//
// [ ] 2. JwtService
//        → Agregar variables de duración: accessExpirationMs y refreshExpirationMs
//        → Refactorizar generarToken() para que reciba un parámetro extra "tipo"
//          (access/refresh) y reutilizarlo en ambos métodos
//
// [ ] 3. JwtService
//        → Metodo esRefreshToken(accessToken) que valide el claim "tipo" y retorne boolean
//
// [ ] 4. ServicioAuth → login()
//        → Generar accessToken y refreshToken
//        → Guardar refreshToken en el User (repositorioUsuario.save)
//        → Devolver ambos tokens en la respuesta
//
// [ ] 5. ServicioAuth
//        → Metodo refrescarToken(refreshTokenRecibido):
//          - Validar que sea tipo "refresh" (esRefreshToken)
//          - Extraer teléfono y validar firma/expiración (extraerTelefono)
//          - Verificar que coincida con el guardado en BD
//          - Generar y devolver accessToken nuevo
//
// [ ] 6. AuthController
//        → Endpoint POST /auth/refresh que reciba el refreshToken y llame al método anterior
//
// [ ] 7. Frontend
//        → Automatizar el llamado a /auth/refresh cuando el access accessToken expire
//          (ver explicación abajo)
package com.acm.tiendaerick.autenticacion.controlador;

import com.acm.tiendaerick.autenticacion.authDTO.LoginDTO;
import com.acm.tiendaerick.autenticacion.authDTO.RespuestaDTO;
import com.acm.tiendaerick.autenticacion.servicio.AutenticacionServicio;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.acm.tiendaerick.autenticacion.authDTO.NuevaContraDTO;

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

}

package com.acm.tiendaerick.autenticacion.servicio;

import com.acm.tiendaerick.autenticacion.authDTO.LoginDTO;
import com.acm.tiendaerick.autenticacion.authDTO.RespuestaDTO;
import com.acm.tiendaerick.autenticacion.repository.RepositorioUsuario;
import com.acm.tiendaerick.autenticacion.usuario.User;
import com.acm.tiendaerick.excepciones.ExcepcionesTienda;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutenticacionServicio {

    private final RepositorioUsuario repositorioUsuario;
    private final PasswordEncoder passwordEncoder;

    public AutenticacionServicio(RepositorioUsuario repositorioUsuario, PasswordEncoder passwordEncoder) {
        this.repositorioUsuario = repositorioUsuario;
        this.passwordEncoder = passwordEncoder;
    }

    public RespuestaDTO login (LoginDTO loginDTO){

        //Bucamos el usuario por su numero de telefono
        User user = repositorioUsuario.findByNumeroTelefono(loginDTO.numeroTelefono()).orElseThrow(() -> new ExcepcionesTienda("Usuario no encontrado"));

        // validamos que el password sea correcto
        if(!passwordEncoder.matches(loginDTO.password(), user.getPassword())){
            throw new ExcepcionesTienda("Contraseña incorrecta");
        }

        //TODO: generar el token con una clase jwtService

        return new RespuestaDTO(user.getToken(), user.getNombre(), user.getId());
    }

}

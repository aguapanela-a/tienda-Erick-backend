package com.acm.tiendaerick.autenticacion.servicio;

import com.acm.tiendaerick.autenticacion.authDTO.LoginDTO;
import com.acm.tiendaerick.autenticacion.authDTO.NuevaContraDTO;
import com.acm.tiendaerick.autenticacion.authDTO.RespuestaDTO;
import com.acm.tiendaerick.compartido.usuarios.entidad.User;
import com.acm.tiendaerick.compartido.usuarios.servicio.ServicioUsuario;
import com.acm.tiendaerick.excepciones.ExcepcionesTienda;
import com.acm.tiendaerick.compartido.seguridad.servicio.JwtServicio;
import com.nimbusds.jose.JOSEException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutenticacionServicio {


    private final ServicioUsuario servicioUsuario;
    private final PasswordEncoder passwordEncoder;
    private final JwtServicio jwtServicio;


    public AutenticacionServicio(ServicioUsuario servicioUsuario, PasswordEncoder passwordEncoder, JwtServicio jwtServicio) {
        this.servicioUsuario = servicioUsuario;
        this.passwordEncoder = passwordEncoder;
        this.jwtServicio = jwtServicio;
    }

    public RespuestaDTO login (LoginDTO loginDTO) throws JOSEException {

        //Bucamos el usuario por su numero de telefono
        User user = servicioUsuario.findByNumeroTelefono(loginDTO.numeroTelefono());

        // validamos que el password sea correcto
        if(!passwordEncoder.matches(loginDTO.password(), user.getContrasena())){
            throw new ExcepcionesTienda("Contraseña incorrecta");
        }

        //generar token con jwt
        String token = jwtServicio.generateToken(user);

        return new RespuestaDTO(token, user.getNombre(), user.getId());
    }

    public String cambiarContrasena(NuevaContraDTO dto){
        // Traer al usuario por su numero de telefono
        User usuario = servicioUsuario.findByNumeroTelefono(dto.telefono());

        // Verificar que la contraseña antigua sea correcta
        if (!passwordEncoder.matches(dto.contrasenaVieja(), usuario.getContrasena())) {
            throw new ExcepcionesTienda("Contraseña antigua incorrecta");
        }

        // Si fue correcta, cambiar la contraseña a la nueva
        return  servicioUsuario.cambiarContrasena(dto.contrasenaNueva(), usuario);
    }

}

package com.acm.tiendaerick.autenticacion.servicio;

import com.acm.tiendaerick.autenticacion.authDTO.AccessTokenDTO;
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

import java.text.ParseException;

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

        //generar accessToken con jwt (dura 1 hora)
        String accessToken = jwtServicio.generateAccessToken(user);

        //generar refreshToken con jwt (dura 1 semana)
        String refreshToken = jwtServicio.generateRefreshToken(user);

        //asignar refreshToken al usuario y guardarlo en la base de datos
        user.setRefreshToken(refreshToken);
        servicioUsuario.guardarUsuario(user);

        return new RespuestaDTO(accessToken, refreshToken, user.getNombre(), user.getId());
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

    public AccessTokenDTO refrescarToken(String tokenRefresh) throws ParseException, JOSEException {

        //Verificar que sea un token de refresh
        if(!jwtServicio.esTokenRefresh(tokenRefresh)){
            throw new ExcepcionesTienda("El token proporcionado no es un refresh token válido");
        }

        //Validar token y extraer teléfono
        String telefonoUserActual = jwtServicio.extraerTelefono(tokenRefresh);

        //Validar que el refreshToken del usuario coincida con el proporcionado
        if(!servicioUsuario.findByNumeroTelefono(telefonoUserActual).getRefreshToken().equals(tokenRefresh)){
            throw new ExcepcionesTienda("El token de refresh no coincide con el registrado para el usuario");
        }

        //si es refres, es válido y coincide con el refreshToken del usuario, generar nuevo accessToken
        String accessToken = jwtServicio.generateAccessToken(servicioUsuario.findByNumeroTelefono(telefonoUserActual));

        return new AccessTokenDTO(accessToken);
    }

}

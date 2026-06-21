package com.acm.tiendaerick.compartido.usuarios.servicio;

import com.acm.tiendaerick.compartido.usuarios.entidad.User;
import com.acm.tiendaerick.compartido.usuarios.enumeraciones.Rol;
import com.acm.tiendaerick.compartido.usuarios.repositorio.RepositorioUsuario;
import com.acm.tiendaerick.excepciones.ExcepcionesTienda;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ServicioUsuario {

    private final PasswordEncoder passwordEncoder;
    private final RepositorioUsuario repositorioUsuario;

    public ServicioUsuario(PasswordEncoder passwordEncoder, RepositorioUsuario repositorioUsuario) {
        this.passwordEncoder = passwordEncoder;
        this.repositorioUsuario = repositorioUsuario;
    }

    public User crearUsuario(String nombre, String numeroTelefono, String contrasena) {

        //Verificar que el numero de telefono no exista
        if(repositorioUsuario.existsByNumeroTelefono(numeroTelefono)){
            return null;
        }

        User usuario = new User();

        usuario.setNombre(nombre);
        usuario.setNumeroTelefono(numeroTelefono);
        usuario.setContrasena(passwordEncoder.encode(contrasena));

        return repositorioUsuario.save(usuario);

    }

    public String cambiarContrasena(String contrasenaNueva, User usuario) {
        //Cambiar la contrasena
        usuario.setContrasena(passwordEncoder.encode(contrasenaNueva));

        //Guardar el usuario con la nueva contrasena
        repositorioUsuario.save(usuario);
        return "Contraseña cambiada exitosamente";
    }

    //este es para después
    public void crearUsuarioConRole(String nombre, String numeroTelefono, String contrasena, Rol role) {
        User usuario = crearUsuario(nombre, numeroTelefono, contrasena);
        usuario.setRol(role);
        repositorioUsuario.save(usuario);
    }

    public User findByNumeroTelefono(String s) {
        return repositorioUsuario.findByNumeroTelefono(s).orElseThrow(() -> new ExcepcionesTienda("Usuario no encontrado"));
    }

    public void guardarUsuario(User user) {
        repositorioUsuario.save(user);
    }
}

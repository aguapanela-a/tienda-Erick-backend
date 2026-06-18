package com.acm.tiendaerick.compartido.usuarios.servicio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InicializarDatos implements CommandLineRunner {

    private final ServicioUsuario servicioUsuario;

    public InicializarDatos(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }

    //variables
    @Value("${mama.pass}")   private String passMama;
    @Value("${papa.pass}")   private String passPapa;
    @Value("${eri.pass}")    private String passEri;
    @Value("${hermana.pass}") private String passHermana;


    @Override
    public void run(String... args) throws Exception {

        servicioUsuario.crearUsuario("Nury", "3165359770", passMama);
        servicioUsuario.crearUsuario("Don Erick", "3204246552", passPapa);
        servicioUsuario.crearUsuario("Erick", "3115910737", passEri);
        servicioUsuario.crearUsuario("Laura", "3182635265", passHermana);

    }



}

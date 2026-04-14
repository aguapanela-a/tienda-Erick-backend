package com.acm.tiendaerick.paqueteClientes.servicio;

import com.acm.tiendaerick.excepciones.ExcepcionesTienda;
import com.acm.tiendaerick.paqueteClientes.dtoCliente.ClienteDTO;
import com.acm.tiendaerick.paqueteClientes.dtoCliente.ClienteRegistroDTO;
import com.acm.tiendaerick.paqueteClientes.entidad.EntidadCliente;
import com.acm.tiendaerick.paqueteClientes.repositorio.RepositorioCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioCRUDCliente {
    private RepositorioCliente repositorioCliente;

    @Autowired
    public ServicioCRUDCliente(RepositorioCliente repositorioCliente) {
        this.repositorioCliente = repositorioCliente;
    }

    public ClienteDTO registrarCliente(ClienteRegistroDTO registroDTO) {

        //Si ya existe ese nombre que lance una ExcepcionesTienda (que en el controlador de excepciones se maneja)
        if(repositorioCliente.existeClientePorNombre(registroDTO.nombre())){
            throw new ExcepcionesTienda("El cliente " + registroDTO.nombre() + " ya existe en el sistema");
        }

        //Si lo de arribe no se cumple (no existe ese nombre) crea una nueva entidad a partir del DTO
        EntidadCliente cliente = new EntidadCliente();
        cliente.setNombre(registroDTO.nombre());
        cliente.setTipo_cliente(registroDTO.tipo_cliente());

        //Guarda la entidad (aquí la BD asigna la id)
        repositorioCliente.save(cliente);

        //Crea el DTO de retorno con la entidad ya guardada <3
        return new ClienteDTO(cliente.getId_cliente(), cliente.getNombre(),cliente.getTipo_cliente());

    }
}

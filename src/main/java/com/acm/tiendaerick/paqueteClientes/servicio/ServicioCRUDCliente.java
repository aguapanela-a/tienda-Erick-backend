package com.acm.tiendaerick.paqueteClientes.servicio;

import com.acm.tiendaerick.excepciones.ExcepcionesTienda;
import com.acm.tiendaerick.paqueteClientes.dtoCliente.ClienteDTO;
import com.acm.tiendaerick.paqueteClientes.dtoCliente.ClienteRegistroDTO;
import com.acm.tiendaerick.paqueteClientes.entidad.EntidadCliente;
import com.acm.tiendaerick.paqueteClientes.repositorio.RepositorioCliente;
import com.acm.tiendaerick.paqueteClientes.tipoEnum.TipoCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioCRUDCliente {
    private RepositorioCliente repositorioCliente;

    @Autowired
    public ServicioCRUDCliente(RepositorioCliente repositorioCliente) {
        this.repositorioCliente = repositorioCliente;
    }

    private void validarNombre(String nombre){
        if(repositorioCliente.existeClientePorNombre(nombre)){
            throw new ExcepcionesTienda("El cliente con el nombre " + nombre + " ya existe en el sistema");
        }
    }

    public ClienteDTO registrarCliente(ClienteRegistroDTO registroDTO) {

        //Si ya existe ese nombre que lance una ExcepcionesTienda (que en el controlador de excepciones se maneja)
        validarNombre(registroDTO.nombre());

        //Si lo de arriba no se cumple (no existe ese nombre) crea una nueva entidad a partir del DTO
        EntidadCliente cliente = new EntidadCliente();
        cliente.setNombre(registroDTO.nombre());
        cliente.setTipo_cliente(registroDTO.tipo_cliente());

        //Guarda la entidad (aquí la BD asigna la id)
        EntidadCliente entidad = repositorioCliente.save(cliente);

        //Crea el DTO de retorno con la entidad ya guardada <3
        return new ClienteDTO(entidad.getId_cliente(), entidad.getNombre(),entidad.getTipo_cliente());
    }


    public ClienteDTO actualizarCliente(ClienteDTO dtoEntrada) {

        EntidadCliente clienteExistente = repositorioCliente.findById(dtoEntrada.id_cliente()).        //busca el cliente existente por el id del dto que recibe del front
                orElseThrow(() -> new ExcepcionesTienda("El cliente " + dtoEntrada.nombre() + " no existe en el sistema")); //Si no existe esa enmtidad con ese ID lanza ese error

        //Evitar que cambie a un nombre ya existente
        validarNombre(dtoEntrada.nombre());

        //Evitar que un cliente frecuente se vuelva invitado
        if(clienteExistente.getTipo_cliente().equals(TipoCliente.FRECUENTE) && dtoEntrada.tipo_cliente().equals(TipoCliente.INVITADO)){
            throw new ExcepcionesTienda("No se puede actualizar el cliente frecuente " + dtoEntrada.nombre() +" a invitado");
        }

        //si lo de atrás se cumple actualice
        clienteExistente.setNombre(dtoEntrada.nombre());
        clienteExistente.setTipo_cliente(dtoEntrada.tipo_cliente());

        EntidadCliente actualizado = repositorioCliente.save(clienteExistente);

        return new ClienteDTO(actualizado.getId_cliente(), actualizado.getNombre(),actualizado.getTipo_cliente());

    }
}

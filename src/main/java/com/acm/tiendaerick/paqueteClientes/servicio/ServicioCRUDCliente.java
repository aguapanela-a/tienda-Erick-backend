package com.acm.tiendaerick.paqueteClientes.servicio;

import com.acm.tiendaerick.excepciones.ExcepcionesTienda;
import com.acm.tiendaerick.paqueteClientes.dtoCliente.ClienteDTO;
import com.acm.tiendaerick.paqueteClientes.dtoCliente.ConfirmacionDTO;
import com.acm.tiendaerick.paqueteClientes.entidad.EntidadCliente;
import com.acm.tiendaerick.paqueteClientes.repositorio.RepositorioCliente;
import com.acm.tiendaerick.paqueteClientes.tipoEnum.TipoCliente;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioCRUDCliente {
    private final RepositorioCliente repositorioCliente;

    @Autowired
    public ServicioCRUDCliente( RepositorioCliente repositorioCliente) {
        this.repositorioCliente = repositorioCliente;
    }

    private void validarNombre(String nombre){
        if(repositorioCliente.existeClientePorNombre(nombre)){
            throw new ExcepcionesTienda("El cliente con el nombre " + nombre + " ya existe en el sistema");
        }
    }

    public @NonNull EntidadCliente validarExistencia(long id){
        return repositorioCliente.findById(id)  //busca el cliente existente por el id del dto que recibe del front
                .orElseThrow(()->new ExcepcionesTienda("El cliente no existe en el sistema")); //Si no existe esa entidad con ese ID lanza ese error
    }


    public List<ClienteDTO> buscarPorPrefijo(String prefijo){

        //buscarPorIniciales me devuelve una lista de EntidadesCliente por cada prefijo y luego les hago Stream,
        // las mapeo a ClienteDTO cada una y las meto a una List
        return repositorioCliente.buscarPorIniciales(prefijo, Sort.by("nombre"), Limit.of(5)).stream()
                .map(entidadC -> new ClienteDTO(entidadC.getId_cliente(), entidadC.getNombre(), entidadC.getTipo_cliente()))
                .toList();
    }


    public ClienteDTO registrarCliente(@NonNull EntidadCliente entidadC) {

        //Si ya existe ese nombre que lance una ExcepcionesTienda (que en el controlador de excepciones se maneja)
        validarNombre(entidadC.getNombre());

        //Guarda la entidad (aquí la BD asigna la id)
        EntidadCliente entidad = repositorioCliente.save(entidadC);

        //Crea el DTO de retorno con la entidad ya guardada <3
        return new ClienteDTO(entidad.getId_cliente(), entidad.getNombre(),entidad.getTipo_cliente());
    }


    public ClienteDTO actualizarCliente(@NonNull ClienteDTO dtoEntrada) {

        EntidadCliente clienteExistente = validarExistencia(dtoEntrada.id_cliente());

        //Evitar que cambie a un nombre ya existente
        validarNombre(dtoEntrada.nombre());

        //Evitar que un cliente frecuente se vuelva invitado
        if(clienteExistente.getTipo_cliente().equals(TipoCliente.FRECUENTE) && dtoEntrada.tipo_cliente().equals(TipoCliente.INVITADO)){
            throw new ExcepcionesTienda("No se puede actualizar el cliente frecuente " + dtoEntrada.nombre() +" a invitado.");
        }

        //si lo de atrás se cumple actualice
        clienteExistente.setNombre(dtoEntrada.nombre());
        clienteExistente.setTipo_cliente(dtoEntrada.tipo_cliente());

        EntidadCliente actualizado = repositorioCliente.save(clienteExistente);

        return new ClienteDTO(actualizado.getId_cliente(), actualizado.getNombre(),actualizado.getTipo_cliente());

    }


    public ConfirmacionDTO eliminarCliente(@NonNull long id) {

        //Verifica que exista (toca hacerlo en el ServicioCliente xd)
        EntidadCliente clienteAEliminar = validarExistencia(id);

        //si existe crea DTO respuesta
        ConfirmacionDTO confirmacion = new ConfirmacionDTO(clienteAEliminar.getId_cliente(), clienteAEliminar.getSaldo_actual(), "Cliente " + clienteAEliminar.getNombre() + " eliminado correctamente", clienteAEliminar.getNombre());

        //elimina la entidad
        repositorioCliente.delete(clienteAEliminar);

        return confirmacion;
    }

    public ClienteDTO obtenerClientePorId(long id){
        EntidadCliente cliente = validarExistencia(id);
        return new ClienteDTO(cliente.getId_cliente(), cliente.getNombre(), cliente.getTipo_cliente());
    }

}

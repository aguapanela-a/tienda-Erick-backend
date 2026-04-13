package com.acm.tiendaerick.paqueteClientes.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.acm.tiendaerick.paqueteClientes.entidad.EntidadCliente;

public interface RepositorioCliente extends JpaRepository<EntidadCliente, Long> {


    //TODO:
    //Toca hacer el SQL para ponerlo en español e igual al UML xd
    //Solo verifica si existe un cliente con ese nombre igunorando las mayúsculas
    boolean existsEntidadClienteByNombreContainingIgnoreCase(String nombre);

    
}

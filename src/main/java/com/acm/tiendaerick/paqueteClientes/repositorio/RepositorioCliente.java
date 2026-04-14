package com.acm.tiendaerick.paqueteClientes.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.acm.tiendaerick.paqueteClientes.entidad.EntidadCliente;
import org.springframework.data.jpa.repository.Query;

public interface RepositorioCliente extends JpaRepository<EntidadCliente, Long> {


    //sentencia JPQA: Selecciona si el conteo de clientes es mayor a cero dónde el nombre coincida
    @Query("SELECT count(cliente) > 0 FROM EntidadCliente cliente where LOWER(cliente.nombre) = LOWER(:nombre) ")
    boolean existeClientePorNombre(String nombre);

    
}

package com.acm.tiendaerick.paqueteClientes.repositorio;

import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.acm.tiendaerick.paqueteClientes.entidad.EntidadCliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepositorioCliente extends JpaRepository<EntidadCliente, Long> {


    //sentencia JPQL: Selecciona si el conteo de clientes es mayor a cero dónde el nombre coincida
    @Query("SELECT count(cliente) > 0 FROM EntidadCliente cliente where LOWER(cliente.nombre) = LOWER(:nombre) ")
    boolean existeClientePorNombre(String nombre);

    //JPQL: que agarra los nombres de los clientes los pone mayus y compara con LIKE que lo que hace es agarrar la
    // letra ingresada y compara con las primeras letras de cada nombre de la tabla y el '%' es para decirle
    // que "cualquier cosa después de esa letra"
    @Query("SELECT c FROM EntidadCliente c WHERE UPPER(c.nombre) LIKE UPPER(CONCAT(:nombre, '%'))")
    List<EntidadCliente> buscarPorIniciales(@Param("nombre") String nombre, Sort sort, Limit limit);


}

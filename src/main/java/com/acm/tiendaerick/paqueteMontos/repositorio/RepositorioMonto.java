package com.acm.tiendaerick.paqueteMontos.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.acm.tiendaerick.paqueteMontos.entidad.EntidadMonto;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RepositorioMonto extends JpaRepository<EntidadMonto, Long>{
    
    //Buscar todos los montos de un cliente
    //@Query(value = "SELECT * FROM montos WHERE id_cliente =?1", nativeQuery=true)  //Query de la consulta
    @Query("SELECT montos FROM EntidadMonto montos WHERE montos.cliente.id_cliente = :id_cliente ")
    public List<EntidadMonto> buscarPorCliente(@Param("id_cliente") long id_cliente);    //Podemos hacer que el tipo sea Page para realizar paginación

    //Elimina todos los montos de un cliente
    @Modifying
    @Transactional
    //@Query(value = "DELETE FROM montos WHERE id_cliente = ?1", nativeQuery=true)
    @Query("DELETE FROM EntidadMonto montos where montos.cliente.id_cliente = :id_cliente")
    public void borrarTodosLosMontos(@Param("id_cliente") long id_cliente);

}
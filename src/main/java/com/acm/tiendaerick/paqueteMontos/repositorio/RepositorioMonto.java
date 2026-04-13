package com.acm.tiendaerick.paqueteMontos.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.acm.tiendaerick.paqueteMontos.entidad.EntidadMonto;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RepositorioMonto extends JpaRepository<EntidadMonto, Long>{
    
    //Buscar todos los montos de un cliente
    @Query(value = "SELECT * FROM montos WHERE id_cliente = ?1", nativeQuery=true)  //Query de la consulta
    public List<EntidadMonto> buscarPorCliente(Long id_cliente);    //Podemos hacer que el tipo sea Page para realizar paginación

    //Elimina todos los montos de un cliente
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM montos WHERE id_cliente = ?1", nativeQuery=true)
    public void borrarTodosLosMontos(Long id_cliente);

}
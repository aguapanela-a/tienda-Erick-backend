package com.acm.tiendaerick.paqueteClientes.repositorio;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.acm.tiendaerick.paqueteClientes.entidad.EntidadCInvitado;

@Repository
public interface RepositorioCInvitado extends JpaRepository<EntidadCInvitado, Long>{

    @Modifying  //Para avisarle a Spring que este metodo es para modificar
    @Transactional //Para que deje borrar/escribir
    //@Query(value = "DELETE FROM expiracion_invitado WHERE fecha_expiracion < ?1", nativeQuery=true)
    @Query("DELETE FROM EntidadCInvitado e WHERE e.fecha_expiracion IS NOT NULL AND e.fecha_expiracion <= :fecha_actual")
    public void eliminarClienteVencido(@Param("fecha_actual") LocalDateTime fecha_actual);

    @Modifying  //Para avisarle a Spring que este metodo es para modificar
    @Transactional //Para que deje borrar/escribir
    @Query("DELETE FROM EntidadCInvitado e WHERE e.id_cliente = :id")
    public void eliminarClientePorID(@Param("id") long id);
    
}

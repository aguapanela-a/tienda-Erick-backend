package com.acm.tiendaerick.paqueteClientes.repositorio;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.acm.tiendaerick.paqueteClientes.entidad.EntidadCInvitado;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RepositorioCInvitado extends JpaRepository<EntidadCInvitado, Long>{

    @Modifying  //Para avisarle a Spring que este metodo es para modificar
    @Transactional //Para que deje borrar
    @Query(value = "DELETE FROM expiracion_invitado WHERE fecha_expiracion < ?1", nativeQuery=true)
    public void eliminarClienteVencido(Date fecha_expiracion);
}

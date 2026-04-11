package com.acm.tiendaerick.paqueteClientes.repositorio;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.acm.tiendaerick.paqueteClientes.entidad.EntidadCInvitado;

@Repository
public interface RepositorioCInvitado extends JpaRepository<EntidadCInvitado, Long>{

    @Query(value = "DELETE FROM expiracion_invitado WHERE fecha_expiracion < ?1", nativeQuery=true)
    public void eliminarClienteVencido(Date fecha_expiracion);
}

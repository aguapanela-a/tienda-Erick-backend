package com.acm.tiendaerick.paqueteClientes.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.acm.tiendaerick.paqueteClientes.entidad.EntidadCliente;

public interface RepositorioCliente extends JpaRepository<EntidadCliente, Long> {
    
}

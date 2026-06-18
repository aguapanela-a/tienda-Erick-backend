package com.acm.tiendaerick.compartido.usuarios.repositorio;

import com.acm.tiendaerick.compartido.usuarios.entidad.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositorioUsuario extends JpaRepository<User, Long> {
    Optional<User> findByNumeroTelefono(String numeroTelefono);

    boolean existsByNumeroTelefono(String numeroTelefono);
}

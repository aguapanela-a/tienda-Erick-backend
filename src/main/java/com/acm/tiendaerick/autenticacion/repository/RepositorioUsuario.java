package com.acm.tiendaerick.autenticacion.repository;

import com.acm.tiendaerick.autenticacion.usuario.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositorioUsuario extends JpaRepository<User, Long> {
    Optional<User> findByNumeroTelefono(String numeroTelefono);
}

package com.acm.tiendaerick.autenticacion.usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class User implements UserDetails {
    @Id
    private UUID id;

    @Column(unique = true)
    private String nombre;

    @Column(unique = true)
    private String contrasena;

    @Column(unique = true)
    private String numeroTelefono;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return this.contrasena;
    }

    @Override
    public String getUsername() {
        return this.nombre;
    }
}

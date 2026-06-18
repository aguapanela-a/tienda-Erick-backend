package com.acm.tiendaerick.compartido.usuarios.entidad;

import com.acm.tiendaerick.compartido.usuarios.enumeraciones.Rol;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class User {
    @Id
    private UUID id;

    @Column(unique = true)
    private String nombre;

    @Column(unique = true)
    private String contrasena;

    @Column(unique = true)
    private String numeroTelefono;

    @Nullable
    private Rol rol;
}

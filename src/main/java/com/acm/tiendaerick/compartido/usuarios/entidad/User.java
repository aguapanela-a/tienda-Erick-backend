package com.acm.tiendaerick.compartido.usuarios.entidad;

import com.acm.tiendaerick.compartido.usuarios.enumeraciones.Rol;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Table(name = "usuarios") //Crea la tabla para la relacion de la fecha y el id del invitado
@Getter @Setter //Getter y setter automático
@NoArgsConstructor //constructor sin argumentos para que JPA lo use
@AllArgsConstructor //constructor con argumentos para que nosotros lo usemos
@SuperBuilder   //Builder para clases heredadas
@Entity //Es una entidad
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) //para que la BD genere los id automáticamente -
    private UUID id;

    @Column(unique = true)
    private String nombre;

    @Column(unique = true)
    private String contrasena;

    @Column(unique = true)
    private String numeroTelefono;

    @Nullable
    @Enumerated(EnumType.STRING)
    private Rol rol;

    @Column(unique = true, columnDefinition = "TEXT")
    private String refreshToken; // para almacenar el refresh accessToken asociado al usuario

}

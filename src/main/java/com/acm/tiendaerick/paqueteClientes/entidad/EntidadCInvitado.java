package com.acm.tiendaerick.paqueteClientes.entidad;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Table(name = "expiracion_invitado") //Crea la tabla para la relacion de la fecha y el id del invitado
@Getter @Setter //Getter y setter automático
@NoArgsConstructor //constructor sin argumentos para que JPA lo use
@AllArgsConstructor //constructor con argumentos para que nosotros lo usemos
@SuperBuilder   //Builder para clases heredadas
@Entity //Es una entidad
public class EntidadCInvitado extends EntidadCliente{
    private Date fecha_expiracion;
}

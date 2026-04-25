package com.acm.tiendaerick.paqueteMontos.entidad;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.acm.tiendaerick.paqueteClientes.entidad.EntidadCliente;
import com.acm.tiendaerick.paqueteMontos.tipoEnum.TipoMonto;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "montos")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class EntidadMonto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_monto;

    private String descripcion;
    private BigDecimal valor;

    @Enumerated(EnumType.STRING) //Esto es para la BD lo lea como String y no como número
    private TipoMonto tipo;
    private LocalDate fecha;

    //mucho a uno: crea una columna "id_cliente" relacionando varios montos a un cliente
    @ManyToOne
    @JoinColumn(name ="id_cliente")
    private EntidadCliente cliente;
}

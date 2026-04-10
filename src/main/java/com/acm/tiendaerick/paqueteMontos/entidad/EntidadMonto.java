package com.acm.tiendaerick.paqueteMontos.entidad;

import com.acm.tiendaerick.paqueteClientes.entidad.EntidadCliente;
import com.acm.tiendaerick.paqueteMontos.tipoEnum.TipoMonto;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Table(name = "montos")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class EntidadMonto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_monto;

    private String descripcion;
    private Double valor;
    private TipoMonto tipo;
    private Date fecha;

    //mucho a uno: crea una columna "id_clientte" relacionando cvarios montos a un cliente
    @ManyToOne
    @JoinColumn(name ="id_cliente")
    private EntidadCliente cliente;
}

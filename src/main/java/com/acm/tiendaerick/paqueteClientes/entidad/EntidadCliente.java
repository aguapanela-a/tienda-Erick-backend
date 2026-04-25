package com.acm.tiendaerick.paqueteClientes.entidad;

import java.math.BigDecimal;
import java.util.List;

import com.acm.tiendaerick.paqueteClientes.tipoEnum.TipoCliente;
import com.acm.tiendaerick.paqueteMontos.entidad.EntidadMonto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity //le dice al JPA que esto es una entidad pa que la guarde
@Table(name = "clientes") //nombre de la tabla
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter // generarlos automáticamente
@NoArgsConstructor //constructor sin argumentos para que JPA lo use
@AllArgsConstructor //constructor con argumentos para que nosotros lo usemos
@SuperBuilder //genera el patrón builder usando @AllArgsConstructor
public class EntidadCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //para que la BD genere los id automáticamente
    private long id_cliente;

    @Column(unique = true)
    private String nombre;

    @Enumerated(EnumType.STRING) //Esto es para la BD lo lea como String y no como número
    private TipoCliente tipo_cliente;

    private double deuda;
    private BigDecimal saldo_actual;

    //cascade = CascadeType.ALL es pa que las cosas que se le hagan al cliente también se le hagan a cada monto
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL) //uno a muchos -> crea una columna "cliente" en la tabla montos dónde pondrá el id del cliente asocuado a varios  montos
    private List<EntidadMonto> movimientos;

}

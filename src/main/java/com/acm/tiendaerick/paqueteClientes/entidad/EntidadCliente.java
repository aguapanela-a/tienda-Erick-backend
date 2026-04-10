package com.acm.tiendaerick.paqueteClientes.entidad;

import com.acm.tiendaerick.paqueteClientes.tipoEnum.TipoCliente;
import com.acm.tiendaerick.paqueteMontos.entidad.EntidadMonto;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Table(name = "clientes") //nombre de la tabla
@Getter @Setter // generarlos automáticamente
@NoArgsConstructor //constructor sin argumentos para que JPA lo use
@AllArgsConstructor //constructor con argumentos para que nosotros lo usemos
@Builder //genera el patrón builder usando @AllArgsConstructor
@Entity //le dice al JPA que esto es una entidad pa que la guarde
public class EntidadCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //para que la BD genere los id automáticamente
    private Long id_cliente;

    private String nombre;
    private TipoCliente tipo_cliente;
    private double deuda;

    private BigDecimal saldo_actual;

    //cascade = CascadeType.ALL es pa que las cosas que se le hagan al cliente también se le hagan a cada monto
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL) //uno a muchos -> crea una columna "cliente" en la tabla montos dónde pondrá el id del cliente asocuado a varios  montos
    private List<EntidadMonto> movimientos;

}

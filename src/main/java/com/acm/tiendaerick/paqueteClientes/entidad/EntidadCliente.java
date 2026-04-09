package com.acm.tiendaerick.paqueteClientes.entidad;

public class EntidadCliente {
    private Long id_cliente;
    private String nombre;
    private TipoCliente tipo_cliente;
    private double deuda;
    private List<EntidadMonto> saldo_actual;

}

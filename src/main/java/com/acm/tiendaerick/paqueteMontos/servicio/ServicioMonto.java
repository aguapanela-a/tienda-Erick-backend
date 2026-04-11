package com.acm.tiendaerick.paqueteMontos.servicio;

import com.acm.tiendaerick.paqueteMontos.repositorio.RepositorioMonto;



public class ServicioMonto {
    private RepositorioMonto repositorio;

    public ServicioMonto(RepositorioMonto repositorio){
        this.repositorio = repositorio; //Esto es agregación, hay que ver si se realiza como composición
    }

    //private MontoDeClienteDTO registrarMonto(MontoDTO monto);

    //public List<MontoDTO> detalleTodosLosMontos(Long idCliente);

    //Método para borrar todos los montos
    public void borrarTodosLosMontos(Long id_cliente){
        this.repositorio.borrarTodosLosMontos(id_cliente);
    }

    //public MontoDeClienteDTO actualizarMontoDeCliente(MontoDeClienteDTO monto);
}

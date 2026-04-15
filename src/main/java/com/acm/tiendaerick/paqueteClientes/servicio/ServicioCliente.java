package com.acm.tiendaerick.paqueteClientes.servicio;

import com.acm.tiendaerick.dtoCompartido.MontoDTO;
import com.acm.tiendaerick.dtoCompartido.MontoDeClienteDTO;
import com.acm.tiendaerick.excepciones.ExcepcionesTienda;
import com.acm.tiendaerick.paqueteClientes.dtoCliente.ClienteDTO;
import com.acm.tiendaerick.paqueteClientes.dtoCliente.ClienteRegistroDTO;
import com.acm.tiendaerick.paqueteClientes.dtoCliente.ConfirmacionDTO;
import com.acm.tiendaerick.paqueteClientes.dtoCliente.DeudaDTO;
import com.acm.tiendaerick.paqueteClientes.tipoEnum.TipoCliente;
import com.acm.tiendaerick.paqueteMontos.servicio.ServicioMonto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Service
public abstract class ServicioCliente {

    private final ServicioCRUDCliente crud;
    private final ServicioMonto servicioMonto;

    @Autowired
    public ServicioCliente(ServicioCRUDCliente crud,  ServicioMonto servicioMonto) {
        this.crud = crud;
        this.servicioMonto = servicioMonto;
    }


    protected abstract boolean aplicarPara(TipoCliente tipoCliente);
    protected abstract void validarReglasDeNegocio(MontoDTO monto);
    abstract ConfirmacionDTO pagarDeuda(long id);
    abstract MontoDeClienteDTO gestionarOperacionMonto(MontoDTO monto);


    public List<ClienteDTO> busquedaCliente(String nombre){

        //Si la barra de búsqueda no tiene nada, entonces retorne una lista en blanco antes de consultar en la BD
        if(nombre == null || nombre.isEmpty()){
            return Collections.emptyList();
        }

        return crud.buscarPorPrefijo(nombre);
    }


    public ClienteDTO registrarCliente(ClienteRegistroDTO registroDTO){
        return crud.registrarCliente(registroDTO);
    }


    public ClienteDTO actualizarCliente(ClienteDTO cliente){
        return crud.actualizarCliente(cliente);
    }


//    public ConfirmacionDTO eliminarCliente(ClienteDTO cliente){
//
//        crud.validarExistencia(cliente.id_cliente());
//
//        BigDecimal deudaDTO = servicioMonto.obtenerDeuda(cliente.id_cliente().getTotal());
//
//        if(deudaDTO.compareTo(BigDecimal.ZERO) > 0){
//            throw new ExcepcionesTienda("No se puede eliminar un cliente con un saldo pendiente a pagar");
//        }
//
//        return crud.eliminarCliente(cliente.id_cliente());
//    }





}

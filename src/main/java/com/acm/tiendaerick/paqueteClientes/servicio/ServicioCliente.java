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

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public abstract class ServicioCliente {

    protected final ServicioCRUDCliente crud;
    protected final ServicioMonto servicioMonto;

    public ServicioCliente(ServicioCRUDCliente crud,  ServicioMonto servicioMonto) {
        this.crud = crud;
        this.servicioMonto = servicioMonto;
    }


    //métodos que las hijas implementarán
    public abstract ClienteDTO registrarCliente(ClienteRegistroDTO registroDTO);
    public abstract boolean aplicarPara(TipoCliente tipoCliente);
    protected abstract void validarReglasDeNegocio(MontoDTO monto);
    public abstract ConfirmacionDTO pagarDeuda(ClienteDTO cliente);


    public MontoDeClienteDTO gestionarOperacionMonto(MontoDTO monto) {
        validarReglasDeNegocio(monto);

        //registra el monto en su tabla (lo relaciona automáticamente al cliente)
        MontoDeClienteDTO respuesta = servicioMonto.registrarMonto(monto.id_cliente(), monto);


        //Le sumo el valor de la deuda al saldo actual del cliente y se lo guardo
        crud.actualizarSaldoActual(monto.id_cliente(), servicioMonto.calcularDeuda(monto.id_cliente()));

        return respuesta;
    }


    //Metodo para obtener el tipo de iun cliente cuando el front no nos lo envía
    public ClienteDTO obtenerClientePorId(long id){
        return crud.obtenerClientePorId(id);
    }


    public List<ClienteDTO> busquedaCliente(String nombre){

        //Si la barra de búsqueda no tiene nada, entonces retorne una lista en blanco antes de consultar en la BD
        if(nombre == null || nombre.isEmpty()){
            return Collections.emptyList();
        }

        return crud.buscarPorPrefijo(nombre);
    }


    public ClienteDTO actualizarCliente(ClienteDTO cliente){
        return crud.actualizarCliente(cliente);
    }


    public ConfirmacionDTO eliminarCliente(ClienteDTO cliente){

        crud.validarExistencia(cliente.id_cliente());

        //compareTo(BigDecimal.ZERO) retorna un -1  si es negativo, 0 si es igual a 0 y 1 si es positivo, entonce al decirle que lace error al ser mayor que 0, solo se cumple si el ]BigDecimal es positovo (el cliente debe dinero)
        if(servicioMonto.calcularDeuda(cliente.id_cliente()).compareTo(BigDecimal.ZERO) > 0){
            throw new ExcepcionesTienda("No se puede eliminar un cliente con un saldo pendiente a pagar");
        }

        return crud.eliminarCliente(cliente.id_cliente());
    }





}

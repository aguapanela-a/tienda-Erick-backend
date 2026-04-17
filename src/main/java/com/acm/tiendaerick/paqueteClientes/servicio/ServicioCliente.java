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


    //métodos que las hijas implementarán
    public abstract boolean aplicarPara(TipoCliente tipoCliente);
    public abstract void validarReglasDeNegocio(MontoDTO monto);
    public abstract ConfirmacionDTO pagarDeuda(long id);
    public abstract MontoDeClienteDTO gestionarOperacionMonto(MontoDTO monto);


    //métodok que calcula el monto total -> solo lo usarán las hijas
    protected BigDecimal calcularDeuda(long id_cliente){
        return servicioMonto.detalleTodosLosMontos(id_cliente).stream()
                .map(monto -> monto.valor())
                .reduce(BigDecimal.ZERO, BigDecimal::add); //Como es BigDecimal no se puede mapear directamente y sumar, por lo tanto se hace un .reduce para que empiece desde 0 y para acumulando cada valor
    }


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


    public DeudaDTO obtenerDeuda(long id_cliente){
        return new DeudaDTO(calcularDeuda(id_cliente), servicioMonto.detalleTodosLosMontos(id_cliente));
    }


    public ConfirmacionDTO eliminarCliente(ClienteDTO cliente){

        crud.validarExistencia(cliente.id_cliente());

        //compareTo(BigDecimal.ZERO) retorna un -1  si es negativo, 0 si es igual a 0 y 1 si es positivo, entonce al decirle que lace error al ser mayor que 0, solo se cumple si el ]BigDecimal es positovo (el cliente debe dinero)
        if(calcularDeuda(cliente.id_cliente()).compareTo(BigDecimal.ZERO) > 0){
            throw new ExcepcionesTienda("No se puede eliminar un cliente con un saldo pendiente a pagar");
        }

        return crud.eliminarCliente(cliente.id_cliente());
    }





}

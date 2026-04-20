package com.acm.tiendaerick.paqueteClientes.servicio;

import com.acm.tiendaerick.excepciones.ExcepcionesTienda;
import com.acm.tiendaerick.paqueteClientes.tipoEnum.TipoCliente;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrquestadorCliente {

    //Agrego las calses hijas de serviciocliente
    private final List<ServicioCliente> serviciosclientes;

    public OrquestadorCliente(List<ServicioCliente> serviciosclientes) {
        this.serviciosclientes = serviciosclientes;
    }


     public ServicioCliente seleccionarServicio(TipoCliente tipoCliente){
        return serviciosclientes.stream()
                .filter(servicioCliente -> servicioCliente.aplicarPara(tipoCliente))
                .findFirst()
                .orElseThrow(() -> new ExcepcionesTienda("El tipo de cliente seleccionado no existe en el sistema :("));
     }
}

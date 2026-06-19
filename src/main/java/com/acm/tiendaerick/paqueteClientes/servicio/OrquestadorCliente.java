package com.acm.tiendaerick.paqueteClientes.servicio;

import com.acm.tiendaerick.paqueteClientes.tipoEnum.TipoCliente;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrquestadorCliente {

    // mapa de servicios, cada tipo de cliente se asocia a un servicio específico
    // usándo patrón Registry porque registra servicios específicos para cada tipo de cliente y los recupera según el tipo solicitado
    private final Map<TipoCliente, ServicioCliente> registry;

    public OrquestadorCliente(List<ServicioCliente> serviciosclientes) {
        this.registry = serviciosclientes.stream()
                .collect(Collectors.toMap(
                        ServicioCliente::obtenerServicio,
                        servicio -> servicio
                ));
    }

    // este metodo recibe el tipo de cliente y devuelve el servicio correspondiente
    public ServicioCliente seleccionarServicioActual(TipoCliente tipo){
        return registry.get(tipo);
     }

}

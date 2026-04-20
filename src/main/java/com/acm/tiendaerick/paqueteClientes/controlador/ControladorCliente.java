package com.acm.tiendaerick.paqueteClientes.controlador;

import com.acm.tiendaerick.paqueteClientes.dtoCliente.ClienteDTO;
import com.acm.tiendaerick.paqueteClientes.dtoCliente.ClienteRegistroDTO;
import com.acm.tiendaerick.paqueteClientes.servicio.OrquestadorCliente;
import com.acm.tiendaerick.paqueteClientes.servicio.ServicioCliente;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
public class ControladorCliente {

    private final OrquestadorCliente orquestador;

    public ControladorCliente(OrquestadorCliente orquestador) {
        this.orquestador = orquestador;
    }


    @PostMapping("/registrar")
    public ResponseEntity<ClienteDTO> registrarCliente(ClienteRegistroDTO clienteRegistroDTO){

        //Seleccionar servicio correcto segùn el tipo con el orqeustador
        ServicioCliente servicioCorrecto = orquestador.seleccionarServicio(clienteRegistroDTO.tipo_cliente());

        //Usa el mètodo implementado por el servicio correcto
        return ResponseEntity.ok(servicioCorrecto.registrarCliente(clienteRegistroDTO));
    }
}

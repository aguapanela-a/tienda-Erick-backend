package com.acm.tiendaerick.paqueteClientes.controlador;

import com.acm.tiendaerick.dtoCompartido.MontoDTO;
import com.acm.tiendaerick.dtoCompartido.MontoDeClienteDTO;
import com.acm.tiendaerick.paqueteClientes.dtoCliente.ClienteDTO;
import com.acm.tiendaerick.paqueteClientes.dtoCliente.ClienteRegistroDTO;
import com.acm.tiendaerick.paqueteClientes.dtoCliente.ConfirmacionDTO;
import com.acm.tiendaerick.paqueteClientes.servicio.OrquestadorCliente;
import com.acm.tiendaerick.paqueteClientes.servicio.ServicioCliente;
import com.acm.tiendaerick.paqueteClientes.tipoEnum.TipoCliente;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ControladorCliente {

    private final OrquestadorCliente orquestador;


    public ControladorCliente(OrquestadorCliente orquestador) {
        this.orquestador = orquestador;
    }


    @PostMapping("/registrar")
    public ResponseEntity<ClienteDTO> registrarCliente(@Valid @RequestBody ClienteRegistroDTO clienteRegistroDTO){

        //Usa el mètodo implementado por el servicio correcto
        return ResponseEntity.ok(
                orquestador
                        .seleccionarServicio(clienteRegistroDTO.tipo_cliente())
                        .registrarCliente(clienteRegistroDTO)
        );
    }


    @PutMapping("/atualisar")
    public ResponseEntity<ClienteDTO> actualizarCliente(@Valid @RequestBody ClienteDTO clienteDTO){
        return ResponseEntity.ok(
                orquestador.
                        seleccionarServicio(clienteDTO.tipo_cliente()).
                        actualizarCliente(clienteDTO)
        );
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<ConfirmacionDTO> eliminarCliente(@Valid @RequestBody ClienteDTO clienteDTO){
        return ResponseEntity.ok(
                orquestador
                        .seleccionarServicio(clienteDTO.tipo_cliente())
                        .eliminarCliente(clienteDTO)
        );
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> busquedaClientes(@RequestParam (name = "search", required = false, defaultValue = "") String nombre){

        return ResponseEntity.ok(
                orquestador.seleccionarServicio(TipoCliente.FRECUENTE)
                        .busquedaCliente(nombre)
        );
    }


    @PutMapping("pagarTodo")
    public ResponseEntity<ConfirmacionDTO> pagarDeuda(@Valid @RequestBody ClienteDTO clienteDTO){
        return ResponseEntity.ok(
                orquestador
                        .seleccionarServicio(clienteDTO.id_cliente(), clienteDTO.tipo_cliente())
                        .pagarDeuda(clienteDTO)
        );
    }


    @PostMapping("/montos")
    public ResponseEntity<MontoDeClienteDTO> agregarMonto(@Valid @RequestBody MontoDTO montoDTO){
        return ResponseEntity.ok(
                orquestador
                        .obtenerServicioPorId(montoDTO.id_cliente())
                        .gestionarOperacionMonto(montoDTO)
        );
    }


}

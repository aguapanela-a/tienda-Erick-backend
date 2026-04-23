package com.acm.tiendaerick.paqueteMontos.controlador;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acm.tiendaerick.dtoCompartido.MontoDTO;
import com.acm.tiendaerick.dtoCompartido.MontoDeClienteDTO;
import com.acm.tiendaerick.paqueteClientes.dtoCliente.DeudaDTO;
import com.acm.tiendaerick.paqueteMontos.servicio.ServicioMonto;





@RestController
@RequestMapping("/montos")
public class ControladorMonto {
    
    private ServicioMonto servicio;

    public ControladorMonto(ServicioMonto servicio){
        this.servicio = servicio;
    }

    @PutMapping("/actualizar/{id_monto}")
    public MontoDeClienteDTO actualizarMontoDeCliente(@PathVariable Long id_monto, @RequestBody MontoDTO monto){
        
        MontoDeClienteDTO monto_actualizado = this.servicio.actualizarMontoDeCliente(id_monto, monto);
        return monto_actualizado;
    }

    @GetMapping("/{id_cliente}")
    public ResponseEntity<DeudaDTO> obtenerDeuda(@PathVariable Long id_cliente) {
        DeudaDTO deuda = this.servicio.obtenerDeuda(id_cliente);
        
        if (deuda == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(deuda);
    }
    

}

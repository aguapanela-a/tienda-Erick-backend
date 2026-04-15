package com.acm.tiendaerick.paqueteMontos.servicio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.acm.tiendaerick.dtoCompartido.MontoDTO;
import com.acm.tiendaerick.dtoCompartido.MontoDeClienteDTO;
import com.acm.tiendaerick.paqueteClientes.entidad.EntidadCliente;
import com.acm.tiendaerick.paqueteMontos.entidad.EntidadMonto;
import com.acm.tiendaerick.paqueteMontos.repositorio.RepositorioMonto;

import lombok.NonNull;



@Service
public class ServicioMonto {
    private RepositorioMonto repositorio;

    public ServicioMonto(RepositorioMonto repositorio){
        this.repositorio = repositorio; //Esto es agregación, hay que ver si se realiza como composición
    }

    private MontoDeClienteDTO registrarMonto(long id_cliente, @NonNull MontoDTO monto){
        //Crear la entidad
        EntidadMonto entidad = new EntidadMonto();
        
        //Se crea el cliente al que pertenece
        EntidadCliente cliente = new EntidadCliente();
        cliente.setId_cliente(id_cliente);

        //Agregar el id_cliente
        entidad.setCliente(cliente);

        //Agregar descripcion
        entidad.setDescripcion(monto.descripcion());

        //Agregar fecha
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(monto.fecha(), formatter);
        entidad.setFecha(date);

        //Agregar valor
        entidad.setValor(monto.valor());

        //Agregar tipo
        entidad.setTipo(monto.tipo_monto());
        
        //Se guarda
        EntidadMonto entidad_guardada = repositorio.save(entidad);

        //Fecha -> String
        String fechaFormateada = entidad_guardada.getFecha().format(formatter);

        return new MontoDeClienteDTO(entidad_guardada.getId_monto(), entidad_guardada.getCliente().getId_cliente(), 
                                     entidad_guardada.getDescripcion(), entidad_guardada.getValor(), 
                                     entidad_guardada.getTipo(), fechaFormateada);
    }

    //public List<MontoDTO> detalleTodosLosMontos(Long idCliente);

    //Método para borrar todos los montos
    public void borrarTodosLosMontos(Long id_cliente){
        this.repositorio.borrarTodosLosMontos(id_cliente);
    }

    //public MontoDeClienteDTO actualizarMontoDeCliente(MontoDeClienteDTO monto);
}

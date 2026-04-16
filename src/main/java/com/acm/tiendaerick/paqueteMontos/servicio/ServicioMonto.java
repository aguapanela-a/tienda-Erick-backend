package com.acm.tiendaerick.paqueteMontos.servicio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.acm.tiendaerick.dtoCompartido.MontoDTO;
import com.acm.tiendaerick.dtoCompartido.MontoDeClienteDTO;
import com.acm.tiendaerick.paqueteClientes.entidad.EntidadCliente;
import com.acm.tiendaerick.paqueteMontos.entidad.EntidadMonto;
import com.acm.tiendaerick.paqueteMontos.repositorio.RepositorioMonto;



@Service
public class ServicioMonto {
    private RepositorioMonto repositorio;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public ServicioMonto(RepositorioMonto repositorio){
        this.repositorio = repositorio;
    }

    private MontoDeClienteDTO registrarMonto(long id_cliente, MontoDTO monto){
        //Crear la entidad
        EntidadMonto entidad = crearEntidadDeMontoDTO(id_cliente, monto);
        
        //Se guarda la entidad
        EntidadMonto entidad_guardada = this.repositorio.save(entidad);

        return new MontoDeClienteDTO(entidad_guardada.getId_monto(), entidad_guardada.getCliente().getId_cliente(), 
                                     entidad_guardada.getDescripcion(), entidad_guardada.getValor(), 
                                     entidad_guardada.getTipo(), convLocalDateString(entidad_guardada.getFecha()));
    }

    public List<MontoDTO> detalleTodosLosMontos(Long idCliente){

        //Obtener todos los montos del cliente
        List<EntidadMonto> entidades = repositorio.buscarPorCliente(idCliente);

        //Mapear cada entidad a MontoDTO y convertirlo a lista
        return entidades.stream()
                        .map(entidad -> new MontoDTO(
                                entidad.getCliente().getId_cliente(),
                                entidad.getDescripcion(),
                                entidad.getValor(),
                                entidad.getTipo(),
                                convLocalDateString(entidad.getFecha())
                        ))
                        .collect(Collectors.toList());
    }

    //Método para borrar todos los montos
    public void borrarTodosLosMontos(Long id_cliente){
        this.repositorio.borrarTodosLosMontos(id_cliente);
    }

    public MontoDeClienteDTO actualizarMontoDeCliente(MontoDeClienteDTO monto){
        EntidadMonto entidad = repositorio.findById(monto.id_monto())
                                                      .orElseThrow(() -> new RuntimeException("Monto no encontrado"));
        
        entidad.setDescripcion(monto.descripcion());            //Actualizar la descripcion
        entidad.setValor(monto.valor());                        //Actualizar el valor
        entidad.setTipo(monto.tipo_monto());                    //Actualizar el tipo
        entidad.setFecha(convStringLocalDate(monto.fecha()));   //Actualizar la fecha

        //Save detecta la entidad existente por el id y al "guardarla" realiza un UPDATE
        EntidadMonto entidad_actualizada = repositorio.save(entidad);

        return new MontoDeClienteDTO(entidad_actualizada.getId_monto(), 
                                     entidad_actualizada.getCliente().getId_cliente(), 
                                     entidad_actualizada.getDescripcion(), 
                                     entidad_actualizada.getValor(), 
                                     entidad_actualizada.getTipo(), 
                                     convLocalDateString(entidad_actualizada.getFecha()));
    }

    private EntidadMonto crearEntidadDeMontoDTO(Long id_cliente, MontoDTO monto){
        EntidadMonto entidad = new EntidadMonto();
        
        //Se crea el cliente al que pertenece
        EntidadCliente cliente = new EntidadCliente();
        cliente.setId_cliente(id_cliente);
        
        entidad.setCliente(cliente);                            //Agregar el id_cliente                      
        entidad.setDescripcion(monto.descripcion());            //Agregar descripcion
        entidad.setFecha(convStringLocalDate(monto.fecha()));   //Agregar fecha
        entidad.setValor(monto.valor());                        //Agregar valor
        entidad.setTipo(monto.tipo_monto());                    //Agregar tipo
        
        return entidad;
    }

    private LocalDate convStringLocalDate(String fecha){
        LocalDate date = LocalDate.parse(fecha, formatter);
        return date;
    }

    private String convLocalDateString(LocalDate fecha){
        String fechaFormateada = fecha.format(formatter);
        return fechaFormateada;
    }

}

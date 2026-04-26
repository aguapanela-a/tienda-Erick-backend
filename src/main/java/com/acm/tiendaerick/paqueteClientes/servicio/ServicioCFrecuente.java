package com.acm.tiendaerick.paqueteClientes.servicio;

import com.acm.tiendaerick.dtoCompartido.MontoDTO;
import com.acm.tiendaerick.dtoCompartido.MontoDeClienteDTO;
import com.acm.tiendaerick.excepciones.ExcepcionesTienda;
import com.acm.tiendaerick.paqueteClientes.dtoCliente.ClienteDTO;
import com.acm.tiendaerick.paqueteClientes.dtoCliente.ClienteRegistroDTO;
import com.acm.tiendaerick.paqueteClientes.dtoCliente.ConfirmacionDTO;
import com.acm.tiendaerick.paqueteClientes.entidad.EntidadCliente;
import com.acm.tiendaerick.paqueteClientes.tipoEnum.TipoCliente;
import com.acm.tiendaerick.paqueteMontos.servicio.ServicioMonto;
import com.acm.tiendaerick.paqueteMontos.tipoEnum.TipoMonto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ServicioCFrecuente extends ServicioCliente {

    public ServicioCFrecuente(ServicioCRUDCliente crud, ServicioMonto servicioMonto) {
        super(crud, servicioMonto);
    }

    @Override
    public boolean aplicarPara(TipoCliente tipoCliente) {
        return tipoCliente == TipoCliente.FRECUENTE;
    }


    //Unicamente validar que el monto sea diferente a cero, no importa el tipo porque para freceuntes se vale to
    @Override
    protected void validarReglasDeNegocio(MontoDTO monto) {
        if(monto.valor().compareTo(BigDecimal.ZERO) == 0){
            throw new ExcepcionesTienda("El monto a ingresar debe ser diferente que cero");
        }
        if((crud.validarExistencia(monto.id_cliente()).getSaldo_actual().compareTo(BigDecimal.ZERO) + monto.valor().compareTo(BigDecimal.ZERO) < 0 )){
            throw  new ExcepcionesTienda("El saldo total del cliente no puede ser negativo");
        }
    }


    @Override
    public ClienteDTO registrarCliente(ClienteRegistroDTO registroDTO) {

        //crear la entidad frecuente
        EntidadCliente entidadFrecuente = new EntidadCliente();
        entidadFrecuente.setNombre(registroDTO.nombre());
        entidadFrecuente.setTipo_cliente(registroDTO.tipo_cliente());

        return crud.registrarCliente(entidadFrecuente);
    }


    @Transactional //Etiqueta para que Spring se asegure que realice toda la lógica correctamente, si algo falla deshace los cambios
    @Override
    public ConfirmacionDTO pagarDeuda(ClienteDTO cliente) {

        //guardo la deuda actual
        BigDecimal deudaActual = servicioMonto.calcularDeuda(cliente.id_cliente());

        //Validar que el cliente deba algo, si no debe, pues no se realiza
        if(deudaActual.compareTo(BigDecimal.ZERO) <= 0){
            throw new ExcepcionesTienda("No es posible pagar una deuda que está en cero");
        }

        //Borrar todos los registro
        servicioMonto.borrarTodosLosMontos(cliente.id_cliente());

        //crear nuevo registro tipo deuda
        MontoDTO montoDeudaDTO = new MontoDTO(cliente.id_cliente(), "Consolidación de deuda anterior", deudaActual, TipoMonto.DEUDA, LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        gestionarOperacionMonto(montoDeudaDTO);

        //crear nuevo registro tipo abono
        MontoDTO montoAbonoDTO = new MontoDTO(cliente.id_cliente(), "Se realizó el pago completo de la deuda", deudaActual.negate(), TipoMonto.ABONO, LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).toString());
        gestionarOperacionMonto(montoAbonoDTO);

        //Retorna el DTO de confirmación si toodo salió bien
        return new ConfirmacionDTO(
                cliente.id_cliente(),
                crud.validarExistencia(cliente.id_cliente()).getSaldo_actual(),
                "Se ha realizado el pago total de la deuda del cliente con éxito!",
                cliente.nombre()
        );
    }

}

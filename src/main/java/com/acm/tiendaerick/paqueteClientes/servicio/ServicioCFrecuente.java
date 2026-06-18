package com.acm.tiendaerick.paqueteClientes.servicio;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.acm.tiendaerick.compartido.dtoCompartido.MontoDTO;
import com.acm.tiendaerick.excepciones.ExcepcionesTienda;
import com.acm.tiendaerick.paqueteClientes.dtoCliente.ClienteDTO;
import com.acm.tiendaerick.paqueteClientes.dtoCliente.ClienteRegistroDTO;
import com.acm.tiendaerick.paqueteClientes.dtoCliente.ConfirmacionDTO;
import com.acm.tiendaerick.paqueteClientes.entidad.EntidadCliente;
import com.acm.tiendaerick.paqueteClientes.tipoEnum.TipoCliente;
import com.acm.tiendaerick.paqueteMontos.servicio.ServicioMonto;
import com.acm.tiendaerick.paqueteMontos.tipoEnum.TipoMonto;

import jakarta.transaction.Transactional;

@Service
public class ServicioCFrecuente extends ServicioCliente {

    public ServicioCFrecuente(ServicioCRUDCliente crud, ServicioMonto servicioMonto) {
        super(crud, servicioMonto);
    }


    //TODO: HACER QUE ESTO RETORNE EL TIPO DE CLIENTE EN CUESTION
    @Override
    public boolean aplicarPara(long idCliente) {

        return crud.obtenerClientePorId(idCliente)
                .tipo_cliente() == TipoCliente.FRECUENTE;
    }

    //TODO: BORRAR ESTO
    @Override
    public boolean aplicarPara(long id_cliente, TipoCliente tipoCliente) {

        if(crud.obtenerClientePorId(id_cliente).tipo_cliente() == tipoCliente) {
            return tipoCliente == TipoCliente.FRECUENTE;
        }else{
            return false;
        }
    }
    // OBJETIVO -> HACER QUE ORQUESTADOR TENGA UN MAP <TipoCLiente (hacer metodo retorne frecuente o invitado en cada servicio), Servicio>
    // EJECUTE LOS MÉTODOS DE LA CLASE ABSTRACTA SERVICIOCLIENTE SEGÚN EL ID DEL CLIENTE
    // CON ESE ID DE CLIENTE BUSCARÁ CON EL METODO DE ARRIBA ELLTIPO DE ESE CLIENTE
    // CON EL TIPO DE ESE CLIENTE HARÁ UN servicios.get(tipo_cliente).metodo PARA ASI EJECUTAR EL METODO
    // DEL SERVICIO QUE ESE .GET RETORNE

    @Override
    public boolean aplicarPara(TipoCliente tipoCliente) {
        return tipoCliente == TipoCliente.FRECUENTE;
    }


    //Unicamente validar que el monto sea diferente a cero, validar que el tipo sea congruente con el valor
    @Override
    protected void validarReglasDeNegocio(MontoDTO monto) {
        if(monto.valor().compareTo(BigDecimal.ZERO) == 0){
            throw new ExcepcionesTienda("El monto a ingresar debe ser diferente que cero");
        }

        if (crud.validarExistencia(monto.id_cliente())
                                        .getSaldo_actual()
                                        .add(monto.valor())
                                        .compareTo(BigDecimal.ZERO) < 0) {
            throw new ExcepcionesTienda("El saldo total del cliente no puede ser negativo");
        }
    }


    @Override
    public ClienteDTO registrarCliente(ClienteRegistroDTO registroDTO) {

        //crear la entidad frecuente
        EntidadCliente entidadFrecuente = new EntidadCliente();
        entidadFrecuente.setNombre(registroDTO.nombre());
        entidadFrecuente.setTipo_cliente(registroDTO.tipo_cliente());
        entidadFrecuente.setSaldo_actual(BigDecimal.ZERO);

        return crud.registrarCliente(entidadFrecuente);
    }


    @Transactional //Etiqueta para que Spring se asegure que realice toda la lógica correctamente, si algo falla deshace los cambios
    @Override
    public ConfirmacionDTO pagarDeuda(ClienteDTO cliente) {

        //guardo la deuda actual
        BigDecimal deudaActual = crud.validarExistencia(cliente.id_cliente()).getSaldo_actual();

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

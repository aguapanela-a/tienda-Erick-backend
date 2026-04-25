package com.acm.tiendaerick.paqueteClientes.servicio;

import com.acm.tiendaerick.dtoCompartido.MontoDTO;
import com.acm.tiendaerick.dtoCompartido.MontoDeClienteDTO;
import com.acm.tiendaerick.excepciones.ExcepcionesTienda;
import com.acm.tiendaerick.paqueteClientes.dtoCliente.ClienteDTO;
import com.acm.tiendaerick.paqueteClientes.dtoCliente.ClienteRegistroDTO;
import com.acm.tiendaerick.paqueteClientes.dtoCliente.ConfirmacionDTO;
import com.acm.tiendaerick.paqueteClientes.entidad.EntidadCInvitado;
import com.acm.tiendaerick.paqueteClientes.repositorio.RepositorioCInvitado;
import com.acm.tiendaerick.paqueteClientes.tipoEnum.TipoCliente;
import com.acm.tiendaerick.paqueteMontos.servicio.ServicioMonto;
import org.springframework.scheduling.annotation.Scheduled;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ServicioCInvitado extends ServicioCliente{

    private final RepositorioCInvitado repositorioCInvitado;
    public ServicioCInvitado(ServicioCRUDCliente crud, ServicioMonto servicioMonto, RepositorioCInvitado repositorioCInvitado) {
        super(crud, servicioMonto);
        this.repositorioCInvitado = repositorioCInvitado;
    }

    @Override
    public boolean aplicarPara(TipoCliente tipoCliente) {
        return tipoCliente.name().equalsIgnoreCase("INVITADO");
    }


    //Validar que sea mayor que cero y que no sea abono
    @Override
    protected void validarReglasDeNegocio(MontoDTO monto) {
        if(monto.valor().compareTo(BigDecimal.ZERO) >= 0){
            throw new ExcepcionesTienda("El monto a ingresar debe ser mayor que cero");
        }
        if(monto.tipo_monto().name().equalsIgnoreCase("ABONO")){
            throw new ExcepcionesTienda("El monto a ingresar NO puede ser un abono para un cliente invitado");
        }
    }


    @Override
    public ClienteDTO registrarCliente(ClienteRegistroDTO registroDTO) {

        EntidadCInvitado clienteCInvitado = new EntidadCInvitado();
        clienteCInvitado.setNombre(registroDTO.nombre());
        clienteCInvitado.setTipo_cliente(registroDTO.tipo_cliente());
        clienteCInvitado.setFecha_expiracion(LocalDateTime.now().plusHours(24));

        return crud.registrarCliente(clienteCInvitado);
    }


    @Override
    public ConfirmacionDTO pagarDeuda(ClienteDTO cliente) {
        servicioMonto.borrarTodosLosMontos(cliente.id_cliente());
        return new ConfirmacionDTO(
                cliente.id_cliente(),
                servicioMonto.calcularDeuda(cliente.id_cliente()),
                "El pago se ha completado con éxito!",
                cliente.nombre()
        );
    }

    @Override
    public MontoDeClienteDTO gestionarOperacionMonto(MontoDTO monto) {
        validarReglasDeNegocio(monto);
        return servicioMonto.registrarMonto(monto.id_cliente(), monto);
    }


    //metodo que se ejecuta automáticamente un minuto luego de iniciar la app, y luego cada dos horas: verifica la fecha de exp de todos los inv y borra los que ya expiraron
    @Scheduled(initialDelay = 60000, fixedRate = 7_200_000)
    public void autoborrado(){
        repositorioCInvitado.eliminarClienteVencido(LocalDateTime.now());
    }
}

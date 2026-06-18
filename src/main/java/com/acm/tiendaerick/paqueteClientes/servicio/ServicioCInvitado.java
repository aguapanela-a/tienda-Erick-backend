package com.acm.tiendaerick.paqueteClientes.servicio;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.acm.tiendaerick.compartido.dtoCompartido.MontoDTO;
import com.acm.tiendaerick.excepciones.ExcepcionesTienda;
import com.acm.tiendaerick.paqueteClientes.dtoCliente.ClienteDTO;
import com.acm.tiendaerick.paqueteClientes.dtoCliente.ClienteRegistroDTO;
import com.acm.tiendaerick.paqueteClientes.dtoCliente.ConfirmacionDTO;
import com.acm.tiendaerick.paqueteClientes.entidad.EntidadCInvitado;
import com.acm.tiendaerick.paqueteClientes.repositorio.RepositorioCInvitado;
import com.acm.tiendaerick.paqueteClientes.tipoEnum.TipoCliente;
import com.acm.tiendaerick.paqueteMontos.servicio.ServicioMonto;

@Service
public class ServicioCInvitado extends ServicioCliente{

    private final RepositorioCInvitado repositorioCInvitado;
    public ServicioCInvitado(ServicioCRUDCliente crud, ServicioMonto servicioMonto, RepositorioCInvitado repositorioCInvitado) {
        super(crud, servicioMonto);
        this.repositorioCInvitado = repositorioCInvitado;
    }


    @Override
    public boolean aplicarPara(long idCliente) {

        return crud.obtenerClientePorId(idCliente)
                .tipo_cliente() == TipoCliente.INVITADO;
    }

    @Override
    public boolean aplicarPara(long id_cliente, TipoCliente tipoCliente) {

        if(crud.obtenerClientePorId(id_cliente).tipo_cliente() == tipoCliente) {
            return tipoCliente == TipoCliente.INVITADO;
        }else {
            return false;
        }
    }

    @Override
    public boolean aplicarPara(TipoCliente tipoCliente) {
        return tipoCliente == TipoCliente.INVITADO;
    }


    //Validar que sea mayor que cero y que no sea abono
    @Override
    protected void validarReglasDeNegocio(MontoDTO monto) {
        if(monto.valor().compareTo(BigDecimal.ZERO) <= 0){
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
        clienteCInvitado.setSaldo_actual(BigDecimal.ZERO);
        clienteCInvitado.setFecha_expiracion(LocalDateTime.now().plusHours(24));

        return crud.registrarCliente(clienteCInvitado);
    }


    @Override
    public ConfirmacionDTO pagarDeuda(ClienteDTO cliente) {

        servicioMonto.borrarTodosLosMontos(cliente.id_cliente());
        crud.actualizarSaldoActual(cliente.id_cliente(),  BigDecimal.ZERO);
        return new ConfirmacionDTO(
                cliente.id_cliente(),
                crud.validarExistencia(cliente.id_cliente()).getSaldo_actual(),
                "El pago se ha completado con éxito!",
                cliente.nombre()
        );
    }



    //metodo que se ejecuta automáticamente un minuto luego de iniciar la app, y luego cada dos horas: verifica la fecha de exp de todos los inv y borra los que ya expiraron
    @Scheduled(initialDelay = 60000, fixedRate = 7_200_000)
    public void autoborrado(){
        repositorioCInvitado.eliminarClienteVencido(LocalDateTime.now());
    }

    public void eliminarExpiracion(long idCliente){

        EntidadCInvitado invitado = repositorioCInvitado
                .findById(idCliente)
                .orElseThrow(() ->
                        new ExcepcionesTienda("Cliente invitado no encontrado"));

        invitado.setFecha_expiracion(null);

        repositorioCInvitado.save(invitado);
    }


    @Override
    public ClienteDTO actualizarCliente(ClienteDTO cliente){
        ClienteDTO response = crud.actualizarCliente(cliente);

        //Si el cambio fue de invitado a frecuente, eliminamos el registro en el repository de invitado
        if(response.tipo_cliente().equals(TipoCliente.FRECUENTE)){
            eliminarExpiracion(response.id_cliente());
        }

        return response;
    }
}

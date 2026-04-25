package com.acm.tiendaerick.paqueteClientes.servicio;

import com.acm.tiendaerick.dtoCompartido.MontoDTO;
import com.acm.tiendaerick.dtoCompartido.MontoDeClienteDTO;
import com.acm.tiendaerick.paqueteClientes.dtoCliente.ClienteDTO;
import com.acm.tiendaerick.paqueteClientes.dtoCliente.ClienteRegistroDTO;
import com.acm.tiendaerick.paqueteClientes.dtoCliente.ConfirmacionDTO;
import com.acm.tiendaerick.paqueteClientes.repositorio.RepositorioCInvitado;
import com.acm.tiendaerick.paqueteClientes.repositorio.RepositorioCliente;
import com.acm.tiendaerick.paqueteClientes.tipoEnum.TipoCliente;
import com.acm.tiendaerick.paqueteMontos.servicio.ServicioMonto;

public class ServicioCInvitado extends ServicioCliente{

    private final RepositorioCInvitado repositorioCInvitado;
    public ServicioCInvitado(ServicioCRUDCliente crud, ServicioMonto servicioMonto, RepositorioCInvitado repositorioCInvitado) {
        super(crud, servicioMonto);
        this.repositorioCInvitado = repositorioCInvitado;
    }

    @Override
    public ClienteDTO registrarCliente(ClienteRegistroDTO registroDTO) {
        return null;
    }

    @Override
    public boolean aplicarPara(TipoCliente tipoCliente) {
        return false;
    }

    @Override
    protected void validarReglasDeNegocio(MontoDTO monto) {

    }

    @Override
    public ConfirmacionDTO pagarDeuda(ClienteDTO cliente) {
        return null;
    }

    @Override
    public MontoDeClienteDTO gestionarOperacionMonto(MontoDTO monto) {
        return null;
    }
}

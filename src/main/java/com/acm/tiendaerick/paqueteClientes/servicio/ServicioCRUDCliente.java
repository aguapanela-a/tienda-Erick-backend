package com.acm.tiendaerick.paqueteClientes.servicio;

import com.acm.tiendaerick.paqueteClientes.dtoCliente.ClienteDTO;
import com.acm.tiendaerick.paqueteClientes.dtoCliente.ClienteRegistroDTO;
import com.acm.tiendaerick.paqueteClientes.repositorio.RepositorioCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioCRUDCliente {
    private RepositorioCliente repositorioCliente;

    @Autowired
    public ServicioCRUDCliente(RepositorioCliente repositorioCliente) {
        this.repositorioCliente = repositorioCliente;
    }

//    public ClienteDTO registrarCliente(ClienteRegistroDTO registroDTO) {
//        if(repositorioCliente.existeClientePorNombre(registroDTO.nombre())){
//
//        }
//    }
}

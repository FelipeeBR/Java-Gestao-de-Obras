package com.sgo.dao;

import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import com.sgo.controller.ClienteJpaController;
import com.sgo.model.Cliente;

/**
 *
 * @author felip
 */
public class ClienteDAO {
    private final ClienteJpaController clienteController;
    private final  EntityManagerFactory emf;
    
    public ClienteDAO(){
        emf=Persistence.createEntityManagerFactory("SGOPU");
        clienteController = new ClienteJpaController(emf);
    }
    
    public void addCliente(Cliente c) throws Exception{
        clienteController.create(c);
    }
    
    public void editCliente(Cliente c) throws Exception{
        clienteController.edit(c);
    }
    
    public void removeCliente(int clienteID) throws Exception{
        clienteController.destroy(clienteID);
    }
    
    public List<Cliente> getAllCliente(){
        return clienteController.findClienteEntities();
    }
    
    public Cliente getClienteById(int clienteID){
        return clienteController.findCliente(clienteID);
    }
}

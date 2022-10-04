package com.sgo.dao;

import com.sgo.controller.ServicoJpaController;
import com.sgo.model.Servico;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author felip
 */
public class ServicoDAO {
    private final ServicoJpaController servicoController;
    private final  EntityManagerFactory emf;
    
    public ServicoDAO(){
        emf=Persistence.createEntityManagerFactory("SGOPU");
        servicoController = new ServicoJpaController(emf);
    }
    
    public void addServico(Servico c) throws Exception{
        servicoController.create(c);
    }
    
    public void editServico(Servico c) throws Exception{
        servicoController.edit(c);
    }
    
    public void removeServico(int servicoID) throws Exception{
        servicoController.destroy(servicoID);
    }
    
    public List<Servico> getAllServico(){
        return servicoController.findServicoEntities();
    }
    
    public Servico getServicoById(int servicoID){
        return servicoController.findServico(servicoID);
    }
}

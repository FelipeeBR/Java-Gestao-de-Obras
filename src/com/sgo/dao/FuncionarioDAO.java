package com.sgo.dao;

import com.sgo.controller.FuncionarioJpaController;
import com.sgo.model.Funcionario;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author felip
 */
public class FuncionarioDAO {
    private final FuncionarioJpaController FuncionarioController;
    private final  EntityManagerFactory emf;
    
    public FuncionarioDAO(){
        emf=Persistence.createEntityManagerFactory("SGOPU");
        FuncionarioController = new FuncionarioJpaController(emf);
    }
    
    public void addFuncionario(Funcionario c) throws Exception{
        FuncionarioController.create(c);
    }
    
    public void editFuncionario(Funcionario c) throws Exception{
        FuncionarioController.edit(c);
    }
    
    public void removeFuncionario(int funcionarioID) throws Exception{
        FuncionarioController.destroy(funcionarioID);
    }
    
    public List<Funcionario> getAllFuncionario(){
        return FuncionarioController.findFuncionarioEntities();
    }
    
    public Funcionario getFuncionarioById(int funcionarioID){
        return FuncionarioController.findFuncionario(funcionarioID);
    }
}

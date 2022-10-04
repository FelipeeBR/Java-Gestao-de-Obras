/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sgo.dao;

import com.sgo.controller.UsuarioJpaController;
import com.sgo.controller.exceptions.NonexistentEntityException;
import com.sgo.model.Usuario;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author felip
 */
public class UsuarioDAO {
    private final UsuarioJpaController usuarioController;
    private final EntityManagerFactory emf;
    public UsuarioDAO(){
        emf = Persistence.createEntityManagerFactory("SGOPU");
        usuarioController = new UsuarioJpaController(emf);
    }
    
    public void addUsuario(Usuario usuario) throws Exception{
        usuarioController.create(usuario);
    }
    
    public void editUsuario(Usuario usuario) throws Exception{
        usuarioController.edit(usuario);
    }
    
    public void removeUsuario(int usuarioID) throws NonexistentEntityException{
        usuarioController.destroy(usuarioID);
    }
    
    public List<Usuario> getAllUsuario(){
        return usuarioController.findUsuarioEntities();
    }
    
    public Usuario getUsuariobyId(int usuarioID){
        return usuarioController.findUsuario(usuarioID);
    }
}

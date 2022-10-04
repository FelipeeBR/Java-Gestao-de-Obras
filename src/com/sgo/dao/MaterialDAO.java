/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sgo.dao;

import com.sgo.controller.MaterialJpaController;
import com.sgo.model.Material;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author felip
 */
public class MaterialDAO {
    private final MaterialJpaController materialController;
    private final EntityManagerFactory emf;
    
    public MaterialDAO(){
        emf=Persistence.createEntityManagerFactory("SGOPU");
        materialController = new MaterialJpaController(emf);
    }
    
    public void addMaterial(Material c) throws Exception{
        materialController.create(c);
    }
    
    public void editMaterial(Material c) throws Exception{
        materialController.edit(c);
    }
    
    public void removeMaterial(int materialID) throws Exception{
        materialController.destroy(materialID);
    }
    
    public List<Material> getAllMaterial(){
        return materialController.findMaterialEntities();
    }
    
    public Material getMaterialById(int materialID){
        return materialController.findMaterial(materialID);
    }
}

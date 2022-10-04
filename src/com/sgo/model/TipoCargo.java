/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.sgo.model;

/**
 *
 * @author felip
 */
public enum TipoCargo {
    Secretaria("Secretaria"),
    Administrador("Administrador"),
    Coordenador("Coordenador"),
    Engenheiro("Engenheiro");
    
    private String label;
    
    private TipoCargo(String label){
        this.label = label;
    }
}

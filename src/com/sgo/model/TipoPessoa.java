package com.sgo.model;
/**
 *
 * @author felip
 */
public enum TipoPessoa {
    Fisica("Física"),
    Juridica("Jurídica");
    
    private String label;
    
    private TipoPessoa(String label){
        this.label = label;
    }
}


package com.sgo.controller;

import javafx.application.Platform;
import javafx.scene.control.TextField;

/**
 *
 * @author felip
 */
public class MascaraCampo {
    
    private static void limitarTamanhoCampo(TextField textField, Integer tamanho){
        textField.textProperty().addListener((observable, oldValue, newValue) ->{
            if(newValue == null || newValue.length() > tamanho){
                textField.setText(oldValue);
            }
        });
    }
    
    private static void posicionarCursor(TextField textField){
        Platform.runLater(() ->{
            textField.positionCaret(textField.getText().length());
        });
    }
    
    public static void mskNumeroCPF(TextField textField){
        MascaraCampo.limitarTamanhoCampo(textField, 11);
        textField.lengthProperty().addListener((observable, oldValue, newValue) -> {
            String textoDigitado = textField.getText();
            textoDigitado = textoDigitado.replaceAll("[^0-9]", "");
            textField.setText(textoDigitado);
            MascaraCampo.posicionarCursor(textField);
        });
    }
    public static void mskNumeroCNPJ(TextField textField){
        MascaraCampo.limitarTamanhoCampo(textField, 14);
        textField.lengthProperty().addListener((observable, oldValue, newValue) -> {
            String textoDigitado = textField.getText();
            textoDigitado = textoDigitado.replaceAll("[^0-9]", "");
            textField.setText(textoDigitado);
            MascaraCampo.posicionarCursor(textField);
        });
    }    
    public static void mskNumero(TextField textField){
        MascaraCampo.limitarTamanhoCampo(textField, 11);
        textField.lengthProperty().addListener((observable, oldValue, newValue) -> {
            String textoDigitado = textField.getText();
            textoDigitado = textoDigitado.replaceAll("[^0-9]", "");
            textField.setText(textoDigitado);
            MascaraCampo.posicionarCursor(textField);
        });
    }
    public static void mskNumeroTel(TextField textField){
        MascaraCampo.limitarTamanhoCampo(textField, 11);
        textField.lengthProperty().addListener((observable, oldValue, newValue) -> {
            String textoDigitado = textField.getText();
            textoDigitado = textoDigitado.replaceAll("[^0-9]", "");
            textField.setText(textoDigitado);
            MascaraCampo.posicionarCursor(textField);
        });
    }  
}

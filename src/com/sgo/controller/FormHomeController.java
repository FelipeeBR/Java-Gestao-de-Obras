package com.sgo.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author felip
 */
public class FormHomeController implements Initializable {

    
    @FXML
    private Button btnCliente;

    @FXML
    private Button btnFuncionario;

    @FXML
    private Button btnObra;
    
    @FXML
    private Button btnMaterial;
    
    @FXML
    private Button btnUsuario;

    @FXML
    private Label lbNome;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        //lbNome.setText(getLogin);
        btnCliente.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try {
                    Parent parent = FXMLLoader.load(getClass().getResource("/com/sgo/view/formCliente.fxml"));
                    Scene scene = new Scene(parent);
                    Stage stage = new Stage();
                    stage.setTitle("Clientes");
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(FormHomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        btnFuncionario.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try {
                    Parent parent = FXMLLoader.load(getClass().getResource("/com/sgo/view/formFuncionario.fxml"));
                    Scene scene = new Scene(parent);
                    Stage stage = new Stage();
                    stage.setTitle("Funcionarios");
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(FormHomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 
        });
        
        btnObra.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try {
                    Parent parent = FXMLLoader.load(getClass().getResource("/com/sgo/view/formServico.fxml"));
                    Scene scene = new Scene(parent);
                    Stage stage = new Stage();
                    stage.setTitle("Servicos e Obras");
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(FormHomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        btnMaterial.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try {
                    Parent parent = FXMLLoader.load(getClass().getResource("/com/sgo/view/formMaterial.fxml"));
                    Scene scene = new Scene(parent);
                    Stage stage = new Stage();
                    stage.setTitle("Materiais");
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(FormHomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        btnUsuario.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try {
                    Parent parent = FXMLLoader.load(getClass().getResource("/com/sgo/view/formUsuario.fxml"));
                    Scene scene = new Scene(parent);
                    Stage stage = new Stage();
                    stage.setTitle("Usuarios");
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(FormHomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.sgo.controller;

import com.sgo.dao.UsuarioDAO;
import com.sgo.model.Usuario;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author felip
 */
public class FormLoginController implements Initializable {

    @FXML
    private TextField txtnome;

    @FXML
    private PasswordField txtsenha;

    @FXML
    private Button btnlogin;

    @FXML
    private Label txtErro;
    
   // private List<Usuario> listUsuarios = new ArrayList<>();
    
    private final ObservableList<Usuario> listUsuarios = FXCollections.observableArrayList();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    void login(ActionEvent event) throws IOException {
        List<Usuario> allUsuario = usuarioDAO.getAllUsuario();        
        for(Usuario usuario: allUsuario){
           if(txtnome.getText().equals(usuario.getLogin())){
               if(txtsenha.getText().equals(usuario.getSenha())){
                   (((Node)event.getSource()).getScene()).getWindow().hide();
                   Parent parent = FXMLLoader.load(getClass().getResource("/com/sgo/view/formHome.fxml"));
                   Scene scene = new Scene(parent);
                   Stage stage = new Stage();
                   stage.setTitle("SGO");
                   stage.setScene(scene);
                   stage.setResizable(false);
                   stage.show();
               }else{
                   txtErro.setText("Senha Inválida.");
               }
           }else{
               txtErro.setText("Usuário Inválido.");
           } 
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

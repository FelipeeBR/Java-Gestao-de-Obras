/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.sgo.controller;

import com.sgo.dao.UsuarioDAO;
import com.sgo.model.Usuario;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author felip
 */
public class FormUsuarioController implements Initializable {

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtLogin;

    @FXML
    private TextField txtSenha;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnApagar;

    @FXML
    private TableView<Usuario> tblUsuario;

    @FXML
    private TableColumn<Usuario, Integer> columnID;

    @FXML
    private TableColumn<Usuario, String> columnLogin;

    @FXML
    private TableColumn<Usuario, String> columnSenha;
    
    private final ObservableList<Usuario> usuariosList = FXCollections.observableArrayList();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuariosList.addAll(usuarioDAO.getAllUsuario());
        
        columnID.setCellValueFactory(new PropertyValueFactory<Usuario,Integer>("id"));
        columnLogin.setCellValueFactory(new PropertyValueFactory<Usuario,String>("login"));
        columnSenha.setCellValueFactory(new PropertyValueFactory<Usuario,String>("senha"));
        
        tblUsuario.setItems(usuariosList);
        
        btnSalvar.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                if(!txtLogin.getText().trim().isEmpty()){
                    Usuario newUsuario = new Usuario(getID());
                    newUsuario.setLogin(txtLogin.getText());
                    newUsuario.setSenha(txtSenha.getText());
                    try {
                        usuarioDAO.addUsuario(newUsuario);
                    } catch (Exception ex) {
                        Logger.getLogger(FormUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    usuariosList.add(newUsuario);
                    clearTextField();
                }
            } 
        });
        
        btnApagar.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                Usuario selectedItem = tblUsuario.getSelectionModel().getSelectedItem();
                if(selectedItem != null){
                    try {
                        usuarioDAO.removeUsuario(selectedItem.getId());
                    } catch (Exception ex) {
                        Logger.getLogger(FormUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    usuariosList.remove(selectedItem);
                    clearTextField();
                }
            }
        
        });
        
        btnEditar.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                Usuario selectedItem = tblUsuario.getSelectionModel().getSelectedItem();
                Usuario newUsuario = usuarioDAO.getUsuariobyId(selectedItem.getId());
                newUsuario.setLogin(txtLogin.getText());
                newUsuario.setSenha(txtSenha.getText());
                try {
                    usuarioDAO.editUsuario(newUsuario);
                } catch (Exception ex) {
                    Logger.getLogger(FormUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
                }
                refreshTable();
                clearTextField();
            }    
        });
        
        tblUsuario.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> 
        {
            if (newVal != null) {
                txtID.setText(newVal.getId().toString());
                txtLogin.setText(newVal.getLogin());
                txtSenha.setText(newVal.getSenha());
            }
        });
    }    
    
    private int getID(){ // Verifica se  ID está sendo usado.
        List<Usuario> allUsuario = usuarioDAO.getAllUsuario();
        int maxID = 0;
        
        if(!allUsuario.isEmpty()){
            for(Usuario usuario: allUsuario){
                if(usuario.getId()>maxID){
                    
                }
            }
        }
        return ++maxID;
    }
    
    public void refreshTable() { // Atualiza o tableView.
        List<Usuario> allUsuario = usuarioDAO.getAllUsuario();
        
        usuariosList.clear();
        
        for(Usuario usuario: allUsuario){
            usuariosList.add(usuario);
        }
        tblUsuario.setItems(usuariosList);
    }
    
    public void clearTextField(){  //limpar os campos
        txtID.clear();
        txtLogin.clear();
        txtSenha.clear();
    }
}

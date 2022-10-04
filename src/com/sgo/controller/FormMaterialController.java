/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.sgo.controller;

import com.sgo.dao.MaterialDAO;
import com.sgo.dao.ServicoDAO;
import com.sgo.model.Material;
import com.sgo.model.Servico;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author felip
 */
public class FormMaterialController implements Initializable {

    @FXML private TextField txtCodigoObra;
    @FXML private TextField txtNomeMaterial;
    @FXML private TextField txtNomeObra;
    @FXML private TextField txtQuantidade;
    @FXML private Button btnSalvar;
    @FXML private Button btnEditar;
    @FXML private Button btnApagar;
    @FXML private TextField txtProcurarObra;
    @FXML private TableView<Servico> tblServico; 
    @FXML private TableColumn<Servico, String> columnServico;
    @FXML private TableColumn<Servico, String> columnNomeObra;
    @FXML private TableView<Material> tblMaterial;   
    @FXML private TableColumn<Material, Integer> columnID;
    @FXML private TableColumn<Material, String> columnMaterial;
    @FXML private TableColumn<Material, Integer> columnQuantidade;
    @FXML private TextField txtPrMaterial;
    @FXML private Button btnRelatorio;
    
    private final ObservableList<Servico> servicosList = FXCollections.observableArrayList();
    private final ObservableList<Material> materiaisList = FXCollections.observableArrayList();
    private final ServicoDAO servicoDAO = new ServicoDAO();
    private final MaterialDAO materialDAO = new MaterialDAO();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        servicosList.addAll(servicoDAO.getAllServico());
        materiaisList.addAll(materialDAO.getAllMaterial());
        
        columnServico.setCellValueFactory(new PropertyValueFactory<Servico,String>("codigo"));
        columnNomeObra.setCellValueFactory(new PropertyValueFactory<Servico,String>("tipoServico"));
        
        columnID.setCellValueFactory(new PropertyValueFactory<Material,Integer>("idServico"));
        columnMaterial.setCellValueFactory(new PropertyValueFactory<Material,String>("nomeMaterial"));
        columnQuantidade.setCellValueFactory(new PropertyValueFactory<Material,Integer>("quantidade"));

        
        tblServico.setItems(servicosList);
        tblMaterial.setItems(materiaisList);
        
        btnSalvar.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                if(!txtNomeMaterial.getText().trim().isEmpty() && !txtQuantidade.getText().trim().isEmpty()
                    && !txtNomeObra.getText().trim().isEmpty()){
                    Material newMaterial = new Material(getID());
                    newMaterial.setIdServico(Integer.valueOf(txtCodigoObra.getText()));
                    newMaterial.setNomeObra(txtNomeObra.getText());
                    newMaterial.setNomeMaterial(txtNomeMaterial.getText());
                    newMaterial.setQuantidade(Integer.valueOf(txtQuantidade.getText()));
                    try {
                        materialDAO.addMaterial(newMaterial);
                    } catch (Exception ex) {
                        Logger.getLogger(FormMaterialController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    materiaisList.add(newMaterial);
                    clearTextField();
                }else
                    JOptionPane.showMessageDialog(null, "Preencha os campos corretamente !");
            } 
        });
        
        btnApagar.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                Material selectedItem = tblMaterial.getSelectionModel().getSelectedItem();
                if(selectedItem != null){
                    try {
                        materialDAO.removeMaterial(selectedItem.getId());
                    } catch (Exception ex) {
                        Logger.getLogger(FormMaterialController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    materiaisList.remove(selectedItem);
                    clearTextField();
                }
            }
        });
        
        btnEditar.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                if(!txtNomeMaterial.getText().trim().isEmpty() && !txtQuantidade.getText().trim().isEmpty()
                    && !txtNomeObra.getText().trim().isEmpty()){
                    Material selectedItem = tblMaterial.getSelectionModel().getSelectedItem();
                    Material newMaterial = materialDAO.getMaterialById(selectedItem.getId());
                    newMaterial.setIdServico(Integer.valueOf(txtCodigoObra.getText()));
                    newMaterial.setNomeObra(txtNomeObra.getText());
                    newMaterial.setNomeMaterial(txtNomeMaterial.getText());
                    newMaterial.setQuantidade(Integer.valueOf(txtQuantidade.getText()));
                    try {
                        materialDAO.editMaterial(newMaterial);
                    } catch (Exception ex) {
                        Logger.getLogger(FormMaterialController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    refreshTable();
                    clearTextField();
                }else
                    JOptionPane.showMessageDialog(null, "Preencha os campos corretamente !");
            }    
        });
                
        btnRelatorio.setOnAction(new EventHandler<ActionEvent> (){
            @Override
            public void handle(ActionEvent event) {
                Connection con;
                String vcodigo = txtPrMaterial.getText();
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    con = DBConnector.getConnection();
                    JasperDesign jdesign = JRXmlLoader.load("src/com/sgo/report/Material.jrxml");

                    JRDesignQuery updateQuery = new JRDesignQuery();
                    updateQuery.setText("SELECT * from material WHERE idServico='"+vcodigo+"'");

                    jdesign.setQuery(updateQuery);
                    JasperReport relatorioCompilado = JasperCompileManager.compileReport(jdesign);

                    JasperPrint relatorioPreenchido = JasperFillManager.fillReport(relatorioCompilado, null, con);

                    JasperViewer.viewReport(relatorioPreenchido, false);

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(FormMaterialController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(FormMaterialController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JRException ex) {
                    Logger.getLogger(FormMaterialController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }     
        });
        
        tblServico.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> 
        {
            if (newVal != null) {
                txtCodigoObra.setText(newVal.getCodigo().toString());
                txtNomeObra.setText(newVal.getTipoServico());
                txtPrMaterial.setText(txtCodigoObra.getText());
            }
        });
        
        tblMaterial.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> 
        {
            if (newVal != null) {
                txtCodigoObra.setText(newVal.getIdServico().toString());
                txtNomeMaterial.setText(newVal.getNomeMaterial());
                txtNomeObra.setText(newVal.getNomeObra());
                txtQuantidade.setText(newVal.getQuantidade().toString());
            }
        });
        
        FilteredList<Material> filteredData = new FilteredList<>(materiaisList, b -> true);
        txtPrMaterial.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(material -> {				
		if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
				
		String lowerCaseFilter = newValue.toLowerCase();
		if(String.valueOf(material.getIdServico()).indexOf(lowerCaseFilter)!=-1) 
                    return true;
                else  
                    return false;
                });
        });  
        SortedList<Material> sortedData = new SortedList<>(filteredData);
	sortedData.comparatorProperty().bind(tblMaterial.comparatorProperty());		
	tblMaterial.setItems(sortedData);
        
        FilteredList<Servico> filterObra = new FilteredList<>(servicosList, b -> true);
        txtProcurarObra.textProperty().addListener((observable, oldValue, newValue) -> {
            filterObra.setPredicate(servico -> {				
		if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
				
		String lowerCaseFilter = newValue.toLowerCase();
		if(servico.getTipoServico().toLowerCase().indexOf(lowerCaseFilter) != -1) 
                    return true;
                else  
                    return false;
                });
        });
        SortedList<Servico> sortedObra = new SortedList<>(filterObra);
	sortedObra.comparatorProperty().bind(tblServico.comparatorProperty());		
	tblServico.setItems(sortedObra);
        MascaraCampo.mskNumero(txtQuantidade);
    }    
    
    private int getID(){ // Verifica se  ID está sendo usado.
        List<Material> allMaterial = materialDAO.getAllMaterial();
        int maxID = 0;
        
        if(!allMaterial.isEmpty()){
            for(Material material: allMaterial){
                if(material.getId()>maxID){
                    
                }
            }
        }
        return ++maxID;
    }
    
    public void refreshTable() { // Atualiza o tableView.
        List<Material> allMaterial = materialDAO.getAllMaterial();
        
        materiaisList.clear();
        
        for(Material material: allMaterial){
            materiaisList.add(material);
        }
        tblMaterial.setItems(materiaisList);
    }
    
    public void clearTextField(){  //limpar os campos
        txtCodigoObra.clear();
        txtNomeMaterial.clear();
        txtNomeObra.clear();
        txtQuantidade.clear();
    }
}

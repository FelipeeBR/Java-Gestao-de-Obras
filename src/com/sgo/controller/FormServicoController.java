package com.sgo.controller;

import com.sgo.dao.ClienteDAO;
import com.sgo.dao.ServicoDAO;
import com.sgo.model.Cliente;
import com.sgo.model.Servico;
import com.sgo.model.StatusObra;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
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
public class FormServicoController implements Initializable {

    @FXML private TextField txtCodigo;
    @FXML private TextField txtServico;
    @FXML private TextField txtPreco;
    @FXML private DatePicker txtData;
    @FXML private ComboBox<StatusObra> txtStatus = new ComboBox<>();
    @FXML private TextField txtCodCliente;
    @FXML private TextField txtRua;
    @FXML private TextField txtBairro;
    @FXML private TextField txtNumero;
    @FXML private TextField txtCidade;
    @FXML private TableView<Servico> tblServico;
    @FXML private TableColumn<Servico, Integer> colCodServico;
    @FXML private TableColumn<Servico, String> colTipoServico;
    @FXML private TableColumn<Servico, String> colData;
    @FXML private TableColumn<Servico, Double> colPreco;
    @FXML private TableColumn<Servico, String> colCodCliente;
    @FXML private TableColumn<Servico, String> colStatus;
    @FXML private TableColumn<Servico, String> colDataTermino;
    @FXML private TextArea txtDescricao;
    @FXML private Button btnSalvar;
    @FXML private Button btnEditar;
    @FXML private Button btnRelatorio; 
    @FXML private Button btnApagar;    
    @FXML private TextField txtSearch;
    @FXML private TableView<Cliente> tblCliente;  
    @FXML private TableColumn<Cliente, String> colNomeCliente;
    @FXML private TableColumn<Cliente, Integer> clienteColumn;
    @FXML private DatePicker txtDataTermino;
    
    
    private final ObservableList<Servico> servicosList = FXCollections.observableArrayList();
    private final ObservableList<Cliente> clientesList = FXCollections.observableArrayList();
    private final ServicoDAO servicoDAO = new ServicoDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    
    @FXML
    void selectStatus(ActionEvent event) {
        String tStatus = txtStatus.getSelectionModel().getSelectedItem().toString();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        servicosList.addAll(servicoDAO.getAllServico());
        clientesList.addAll(clienteDAO.getAllCliente());
        
        colCodServico.setCellValueFactory(new PropertyValueFactory<Servico,Integer>("codigo"));
        colTipoServico.setCellValueFactory(new PropertyValueFactory<Servico,String>("tipoServico"));
        colData.setCellValueFactory(new PropertyValueFactory<Servico,String>("dataObra"));
        colPreco.setCellValueFactory(new PropertyValueFactory<Servico,Double>("preco"));
        colCodCliente.setCellValueFactory(new PropertyValueFactory<Servico,String>("codcliente"));
        colStatus.setCellValueFactory(new PropertyValueFactory<Servico,String>("statusObra"));
        colDataTermino.setCellValueFactory(new PropertyValueFactory<Servico,String>("dataTermino"));
        
        clienteColumn.setCellValueFactory(new PropertyValueFactory<Cliente,Integer>("codcliente"));
        colNomeCliente.setCellValueFactory(new PropertyValueFactory<Cliente,String>("nome"));
        
        tblServico.setItems(servicosList);
        tblCliente.setItems(clientesList);
        
        btnSalvar.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                if(!txtServico.getText().trim().isEmpty() && !txtPreco.getText().trim().isEmpty()
                    && !txtRua.getText().trim().isEmpty() && !txtBairro.getText().trim().isEmpty()
                    &&!txtNumero.getText().trim().isEmpty() && !txtCidade.getText().trim().isEmpty()
                    &&!txtDescricao.getText().trim().isEmpty()){
                    Servico newServico = new Servico(getCodServico());
                    newServico.setTipoServico(txtServico.getText());
                    newServico.setStatusObra(txtStatus.getValue());
                    newServico.setPreco(txtPreco.getText());
                    newServico.setDataObra(txtData.getValue().toString());
                    newServico.setRua(txtRua.getText());
                    newServico.setBairro(txtBairro.getText());
                    newServico.setNumero(txtNumero.getText());
                    newServico.setCidade(txtCidade.getText());
                    newServico.setCodcliente(txtCodCliente.getText());
                    newServico.setDescricao(txtDescricao.getText());
                    newServico.setDataTermino(txtDataTermino.getValue().toString());
                    try {
                        servicoDAO.addServico(newServico);
                    } catch (Exception ex) {
                        Logger.getLogger(FormServicoController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    servicosList.add(newServico);
                    clearTextField();
                }else
                    JOptionPane.showMessageDialog(null, "Preencha os campos corretamente !");
            } 
        });
        
        btnEditar.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                if(!txtServico.getText().trim().isEmpty() && !txtPreco.getText().trim().isEmpty()
                    && !txtRua.getText().trim().isEmpty() && !txtBairro.getText().trim().isEmpty()
                    &&!txtNumero.getText().trim().isEmpty() && !txtCidade.getText().trim().isEmpty()
                    &&!txtDescricao.getText().trim().isEmpty()){
                    Servico selectedItem = tblServico.getSelectionModel().getSelectedItem();
                    Servico newServico = servicoDAO.getServicoById(selectedItem.getCodigo());
                    newServico.setTipoServico(txtServico.getText());
                    newServico.setStatusObra(txtStatus.getValue());
                    newServico.setPreco(txtPreco.getText());
                    newServico.setDataObra(txtData.getValue().toString());
                    newServico.setRua(txtRua.getText());
                    newServico.setBairro(txtBairro.getText());
                    newServico.setNumero(txtNumero.getText());
                    newServico.setCidade(txtCidade.getText());
                    newServico.setCodcliente(txtCodCliente.getText());
                    newServico.setDescricao(txtDescricao.getText());
                    newServico.setDataTermino(txtDataTermino.getValue().toString());
                    try {
                        servicoDAO.editServico(newServico);
                    } catch (Exception ex) {
                        Logger.getLogger(FormServicoController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    refreshTable();
                    clearTextField();
                }else
                    JOptionPane.showMessageDialog(null, "Preencha os campos corretamente !");
            }  
        }); 
        
        btnApagar.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                Servico selectedItem = tblServico.getSelectionModel().getSelectedItem();
                if(selectedItem != null){
                    try {
                        servicoDAO.removeServico(selectedItem.getCodigo());
                    } catch (Exception ex) {
                        Logger.getLogger(FormServicoController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    servicosList.remove(selectedItem);
                    clearTextField();
                }
            }
        
        });
        
        btnRelatorio.setOnAction(new EventHandler<ActionEvent> (){
            @Override
            public void handle(ActionEvent event) {
                Connection con;
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    con = DBConnector.getConnection();
                    JasperDesign jdesign = JRXmlLoader.load("src/com/sgo/report/Servico.jrxml");

                    JRDesignQuery updateQuery = new JRDesignQuery();
                    updateQuery.setText("SELECT * from servico ORDER BY codigo");

                    jdesign.setQuery(updateQuery);
                    JasperReport relatorioCompilado = JasperCompileManager.compileReport(jdesign);

                    JasperPrint relatorioPreenchido = JasperFillManager.fillReport(relatorioCompilado, null, con);

                    JasperViewer.viewReport(relatorioPreenchido, false);

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(FormServicoController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(FormServicoController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JRException ex) {
                    Logger.getLogger(FormServicoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }     
        });
        
        FilteredList<Cliente> filteredData = new FilteredList<>(clientesList, b -> true);
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(cliente -> {
            // If filter text is empty, display all persons.				
		if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
				
		String lowerCaseFilter = newValue.toLowerCase();
                if(cliente.getNome().toLowerCase().indexOf(lowerCaseFilter) != -1) 
                    return true; // Filter matches first name.
                else  
                    return false; // Does not match.
                });
        });
        
        tblCliente.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> 
        {
            if (newVal != null) {
                txtCodCliente.setText(newVal.getCodcliente().toString());
            }
        });
        
        tblServico.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> 
        {
            if (newVal != null) {
                txtCodCliente.setText(newVal.getCodcliente().toString());
                txtServico.setText(newVal.getTipoServico());
                txtDescricao.setText(newVal.getDescricao());
                txtPreco.setText(newVal.getPreco());
                txtRua.setText(newVal.getRua());
                txtBairro.setText(newVal.getBairro());
                txtNumero.setText(newVal.getNumero());
                txtCidade.setText(newVal.getCidade());
                txtData.setValue(LocalDate.parse(newVal.getDataObra()));
                txtDataTermino.setValue(LocalDate.parse(newVal.getDataTermino()));
            }
        });
 
        SortedList<Cliente> sortedData = new SortedList<>(filteredData);
	sortedData.comparatorProperty().bind(tblCliente.comparatorProperty());		
	tblCliente.setItems(sortedData);
        
        txtStatus.getItems().setAll(StatusObra.values());
        MascaraCampo.mskNumero(txtPreco);
        MascaraCampo.mskNumero(txtNumero);
        MascaraCampo.mskNumero(txtCodCliente);
    }    
    
    private int getCodServico(){ // Verifica se  ID está sendo usado.
        List<Servico> allServico = servicoDAO.getAllServico();
        int maxID = 0;
        
        if(!allServico.isEmpty()){
            for(Servico servico: allServico){
                if(servico.getCodigo()>maxID){
                    
                }
            }
        }
        return ++maxID;
    }
    
    public void refreshTable() { // Atualiza o tableView.
        List<Servico> allServico = servicoDAO.getAllServico();
        
        servicosList.clear();
        
        for(Servico servico: allServico){
            servicosList.add(servico);
        }
        tblServico.setItems(servicosList);
    }
    
    public void clearTextField(){  //limpar os campos
        txtServico.clear();
        txtPreco.clear();
        txtCodCliente.clear();
        txtRua.clear();
        txtBairro.clear();
        txtNumero.clear();
        txtCidade.clear();
    }
}

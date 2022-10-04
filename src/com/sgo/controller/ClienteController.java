package com.sgo.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import com.sgo.dao.ClienteDAO;
import com.sgo.model.Cliente;
import com.sgo.model.TipoPessoa;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
/**
 * FXML Controller class
 *
 * @author felip
 */
public class ClienteController implements Initializable {

    @FXML private TableView<Cliente> tblCliente; @FXML private TableColumn<Cliente, Integer> columID;
    @FXML private TableColumn<Cliente, String> colNome;
    @FXML private TableColumn<Cliente, String> colTipoPessoa;
    @FXML private TableColumn<Cliente, String> colCPF;
    @FXML private TableColumn<Cliente, String> colCNPJ;
    @FXML private TableColumn<Cliente, String> colRua;
    @FXML private TableColumn<Cliente, String> colBairro;
    @FXML private TableColumn<Cliente, String> colNumero;
    @FXML private TableColumn<Cliente, String> colCidade;
    @FXML private TableColumn<Cliente, String> colTelefone;
    @FXML private TableColumn<Cliente, String> colEmail;
    @FXML private TextField txtID;
    @FXML private TextField txtNome;
    @FXML private TextField txtPJ;
    @FXML private TextField txtCPF;
    @FXML private TextField txtCNPJ;
    @FXML private TextField txtRua;
    @FXML private TextField txtBairro;
    @FXML private TextField txtNumero;
    @FXML private TextField txtCidade;
    @FXML private TextField txtTelefone;
    @FXML private TextField txtEmail;
    @FXML private Button btnSave;   
    @FXML private Button btnEditar;
    @FXML private Button btnApagar;   
    @FXML private Button btnRelatorio;
    @FXML private ComboBox<TipoPessoa> comboPJ = new ComboBox<>();
    @FXML private Label labelErro;
    
    private final ObservableList<Cliente> clientesList = FXCollections.observableArrayList();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    
    @FXML
    void selectTipoPessoa(ActionEvent event) {
        String tPessoa = comboPJ.getSelectionModel().getSelectedItem().toString();
        txtPJ.setText(tPessoa);
        System.out.println(txtPJ.getText());
        switch(tPessoa){
            case "Fisica":
                txtCPF.setEditable(true);
                txtCPF.requestFocus();
                txtCNPJ.setEditable(false);
                txtCNPJ.setText("0");
                break;
            case "Juridica":
                txtCPF.setText("0");
                txtCPF.setEditable(false);
                txtCNPJ.setEditable(true);
                txtCNPJ.requestFocus();
                break;
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clientesList.addAll(clienteDAO.getAllCliente());
        
        columID.setCellValueFactory(new PropertyValueFactory<Cliente,Integer>("codcliente"));
        colTipoPessoa.setCellValueFactory(new PropertyValueFactory<Cliente,String>("tipopessoa"));
        colNome.setCellValueFactory(new PropertyValueFactory<Cliente,String>("nome"));
        colCPF.setCellValueFactory(new PropertyValueFactory<Cliente,String>("cpf"));
        colCNPJ.setCellValueFactory(new PropertyValueFactory<Cliente,String>("cnpj"));
        colRua.setCellValueFactory(new PropertyValueFactory<Cliente,String>("rua"));
        colBairro.setCellValueFactory(new PropertyValueFactory<Cliente,String>("bairro"));
        colNumero.setCellValueFactory(new PropertyValueFactory<Cliente,String>("numero"));
        colCidade.setCellValueFactory(new PropertyValueFactory<Cliente,String>("cidade"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<Cliente,String>("telefone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Cliente,String>("email"));
        
        tblCliente.setItems(clientesList);
        
        btnSave.setOnAction(new EventHandler<ActionEvent>(){     
            @Override
            public void handle(ActionEvent event) {
                if(!txtNome.getText().trim().isEmpty() && !txtCPF.getText().trim().isEmpty()
                    && !txtCNPJ.getText().trim().isEmpty()
                    && !txtRua.getText().trim().isEmpty() && !txtBairro.getText().trim().isEmpty()
                    && !txtNumero.getText().trim().isEmpty() && !txtCidade.getText().trim().isEmpty()
                    && !txtTelefone.getText().trim().isEmpty() && !txtEmail.getText().trim().isEmpty()){
                    Cliente newCliente = new Cliente(getID());
                    newCliente.setNome(txtNome.getText());
                    newCliente.setTipopessoa(comboPJ.getValue());
                    newCliente.setCpf(txtCPF.getText());
                    newCliente.setCnpj(txtCNPJ.getText());
                    newCliente.setRua(txtRua.getText());
                    newCliente.setBairro(txtBairro.getText());
                    newCliente.setNumero(txtNumero.getText());
                    newCliente.setCidade(txtCidade.getText());
                    newCliente.setTelefone(txtTelefone.getText());
                    newCliente.setEmail(txtEmail.getText());
                    try {
                        
                        clienteDAO.addCliente(newCliente);
                    } catch (Exception ex) {
                        Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    clientesList.add(newCliente);
                    clearTextField();
                }else
                    labelErro.setText("Preencha todos os campos!");
                
            }
        });
        
        btnApagar.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                Cliente selectedItem = tblCliente.getSelectionModel().getSelectedItem();
                if(selectedItem != null){
                    try {
                        clienteDAO.removeCliente(selectedItem.getCodcliente());
                    } catch (Exception ex) {
                        Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    clientesList.remove(selectedItem);
                    clearTextField();
                }
            }
        
        });
        
        btnEditar.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                if(txtNome.getText().length()>0 && txtCPF.getText().length()>0
                    && txtCNPJ.getText().length()>0
                    && txtRua.getText().length()>0 && txtBairro.getText().length()>0
                    && txtNumero.getText().length()>0 && txtCidade.getText().length()>0
                    && txtTelefone.getText().length()>0 && txtEmail.getText().length()>0){
                    Cliente selectedItem = tblCliente.getSelectionModel().getSelectedItem();
                    Cliente newCliente = clienteDAO.getClienteById(selectedItem.getCodcliente());
                    newCliente.setNome(txtNome.getText());
                    newCliente.setTipopessoa(comboPJ.getValue());
                    newCliente.setCpf(txtCPF.getText());
                    newCliente.setCnpj(txtCNPJ.getText());
                    newCliente.setRua(txtRua.getText());
                    newCliente.setBairro(txtBairro.getText());
                    newCliente.setNumero(txtNumero.getText());
                    newCliente.setCidade(txtCidade.getText());
                    newCliente.setTelefone(txtTelefone.getText());
                    newCliente.setEmail(txtEmail.getText());
                    try {
                        clienteDAO.editCliente(newCliente);
                    } catch (Exception ex) {
                        Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    refreshTable();
                    clearTextField();
                }else
                    labelErro.setText("Preencha todos os campos!");
            }    
        });
        
        btnRelatorio.setOnAction(new EventHandler<ActionEvent> (){
            @Override
            public void handle(ActionEvent event) {
                Connection con;
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    con = DBConnector.getConnection();
                    JasperDesign jdesign = JRXmlLoader.load("src/com/sgo/report/Cliente.jrxml");

                    JRDesignQuery updateQuery = new JRDesignQuery();
                    updateQuery.setText("SELECT * from cliente ORDER BY codcliente");

                    jdesign.setQuery(updateQuery);
                    JasperReport relatorioCompilado = JasperCompileManager.compileReport(jdesign);

                    JasperPrint relatorioPreenchido = JasperFillManager.fillReport(relatorioCompilado, null, con);

                    JasperViewer.viewReport(relatorioPreenchido, false);

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JRException ex) {
                    Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }     
        });
        
        tblCliente.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> 
        {
            if (newVal != null) {
                txtNome.setText(newVal.getNome());
                txtPJ.setText(newVal.getTipopessoa().toString());
                txtCPF.setText(newVal.getCpf());
                txtCNPJ.setText(newVal.getCnpj());
                txtRua.setText(newVal.getRua());
                txtBairro.setText(newVal.getBairro());
                txtNumero.setText(newVal.getNumero());
                txtCidade.setText(newVal.getCidade());
                txtTelefone.setText(newVal.getTelefone());
                txtEmail.setText(newVal.getEmail());
            }
        });
        comboPJ.getItems().setAll(TipoPessoa.values());
        MascaraCampo.mskNumeroCPF(txtCPF);
        MascaraCampo.mskNumeroCNPJ(txtCNPJ);
        MascaraCampo.mskNumero(txtNumero);
        MascaraCampo.mskNumeroTel(txtTelefone);
    } 
    
    private int getID(){ // Verifica se  ID está sendo usado.
        List<Cliente> allCliente = clienteDAO.getAllCliente();
        int maxID = 0;
        
        if(!allCliente.isEmpty()){
            for(Cliente cliente: allCliente){
                if(cliente.getCodcliente()>maxID){
                    
                }
            }
        }
        return ++maxID;
    }
    
    
    //Funçoes para Tratar o Front
    
    
    public void refreshTable() { // Atualiza o tableView.
        List<Cliente> allCliente = clienteDAO.getAllCliente();
        
        clientesList.clear();
        
        for(Cliente cliente: allCliente){
            clientesList.add(cliente);
        }
        tblCliente.setItems(clientesList);
    }
    
    public void clearTextField(){  //limpar os campos
        txtNome.clear();
        txtPJ.clear();
        txtCPF.clear();
        txtCNPJ.clear();
        txtRua.clear();
        txtBairro.clear();
        txtNumero.clear();
        txtCidade.clear();
        txtTelefone.clear();
        txtEmail.clear();
    }
}

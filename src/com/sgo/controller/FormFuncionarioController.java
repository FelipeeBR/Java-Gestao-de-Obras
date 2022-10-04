package com.sgo.controller;

import com.sgo.dao.FuncionarioDAO;
import com.sgo.model.Funcionario;
import com.sgo.model.TipoCargo;
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
public class FormFuncionarioController implements Initializable {

    @FXML private TableView<Funcionario> tblFuncionario;
    @FXML private TableColumn<Funcionario, Integer> columID;
    @FXML private TableColumn<Funcionario, String> colNome;
    @FXML private TableColumn<Funcionario, String> colCPF;
    @FXML private TableColumn<Funcionario, String> colTipoCargo;
    @FXML private TableColumn<Funcionario, String> colRua;
    @FXML private TableColumn<Funcionario, String> colBairro;
    @FXML private TableColumn<Funcionario, String> colNumero;
    @FXML private TableColumn<Funcionario, String> colCidade;
    @FXML private TableColumn<Funcionario, String> colTelefone;
    @FXML private TableColumn<Funcionario, String> colEmail;
    @FXML private TableColumn<Funcionario, Double> colSalario;
    @FXML private TableColumn<Funcionario, String> colAdmissao;
    @FXML private Button btnSave; @FXML private Button btnEditar;
    @FXML private Button btnApagar; @FXML private Button btnRelatorio;
    @FXML private TextField txtNome; @FXML private TextField txtCPF;
    @FXML private ComboBox<TipoCargo> boxCargo = new ComboBox<>();
    @FXML private TextField txtCargo; @FXML private TextField txtSalario;
    @FXML private TextField txtEmail; @FXML private TextField txtData;
    @FXML private TextField txtRua; @FXML private TextField txtBairro;
    @FXML private TextField txtNumero; @FXML private TextField txtCidade;
    @FXML private TextField txtTelefone; @FXML private DatePicker opcData;
    
    private final ObservableList<Funcionario> funcionarioList = FXCollections.observableArrayList();
    private final FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

    @FXML
    void selectCargo(ActionEvent event) {
        String tCargo = boxCargo.getSelectionModel().getSelectedItem().toString();
        txtCargo.setText(tCargo);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        funcionarioList.addAll(funcionarioDAO.getAllFuncionario());
        
        columID.setCellValueFactory(new PropertyValueFactory<Funcionario,Integer>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<Funcionario,String>("nome"));
        colCPF.setCellValueFactory(new PropertyValueFactory<Funcionario,String>("cpf"));
        colTipoCargo.setCellValueFactory(new PropertyValueFactory<Funcionario,String>("cargo"));
        colRua.setCellValueFactory(new PropertyValueFactory<Funcionario,String>("rua"));
        colBairro.setCellValueFactory(new PropertyValueFactory<Funcionario,String>("bairro"));
        colNumero.setCellValueFactory(new PropertyValueFactory<Funcionario,String>("numero"));
        colCidade.setCellValueFactory(new PropertyValueFactory<Funcionario,String>("cidade"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<Funcionario,String>("telefone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Funcionario,String>("email"));
        colSalario.setCellValueFactory(new PropertyValueFactory<Funcionario,Double>("salario"));
        colAdmissao.setCellValueFactory(new PropertyValueFactory<Funcionario,String>("dataContrato"));
        
        tblFuncionario.setItems(funcionarioList);
        
        btnSave.setOnAction(new EventHandler<ActionEvent>(){     
            @Override
            public void handle(ActionEvent event) {
                if(!txtNome.getText().trim().isEmpty() && !txtCPF.getText().trim().isEmpty()
                    && !txtSalario.getText().trim().isEmpty() && !txtRua.getText().trim().isEmpty()
                    && !txtBairro.getText().trim().isEmpty() && !txtNumero.getText().trim().isEmpty()
                    && !txtCidade.getText().trim().isEmpty() && !txtTelefone.getText().trim().isEmpty()
                    &&!txtEmail.getText().trim().isEmpty()){
                    Funcionario newFuncionario = new Funcionario(getID());
                    newFuncionario.setNome(txtNome.getText());
                    newFuncionario.setCpf(txtCPF.getText());
                    newFuncionario.setCargo(boxCargo.getValue());
                    newFuncionario.setDataContrato(opcData.getValue().toString());
                    newFuncionario.setSalario(txtSalario.getText());
                    newFuncionario.setRua(txtRua.getText());
                    newFuncionario.setBairro(txtBairro.getText());
                    newFuncionario.setNumero(txtNumero.getText());
                    newFuncionario.setCidade(txtCidade.getText());
                    newFuncionario.setTelefone(txtTelefone.getText());
                    newFuncionario.setEmail(txtEmail.getText());
                    try {
                        funcionarioDAO.addFuncionario(newFuncionario);
                    } catch (Exception ex) {
                        Logger.getLogger(FormFuncionarioController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    funcionarioList.add(newFuncionario);
                    clearTextField();
                }else
                    JOptionPane.showMessageDialog(null, "Preencha os campos corretamente !");
            }
        });
        
        btnApagar.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                Funcionario selectedItem = tblFuncionario.getSelectionModel().getSelectedItem();
                if(selectedItem != null){
                    try {
                        funcionarioDAO.removeFuncionario(selectedItem.getId());
                    } catch (Exception ex) {
                        Logger.getLogger(FormFuncionarioController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    funcionarioList.remove(selectedItem);
                    clearTextField();
                }
            }
        
        });
        
        btnEditar.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                if(!txtNome.getText().trim().isEmpty() && !txtCPF.getText().trim().isEmpty()
                    && !txtSalario.getText().trim().isEmpty() && !txtRua.getText().trim().isEmpty()
                    && !txtBairro.getText().trim().isEmpty() && !txtNumero.getText().trim().isEmpty()
                    && !txtCidade.getText().trim().isEmpty() && !txtTelefone.getText().trim().isEmpty()
                    &&!txtEmail.getText().trim().isEmpty()){
                    Funcionario selectedItem = tblFuncionario.getSelectionModel().getSelectedItem();
                    Funcionario newFuncionario = funcionarioDAO.getFuncionarioById(selectedItem.getId());
                    newFuncionario.setNome(txtNome.getText());
                    newFuncionario.setCpf(txtCPF.getText());
                    newFuncionario.setCargo(boxCargo.getValue());
                    newFuncionario.setDataContrato(opcData.getValue().toString());
                    newFuncionario.setSalario(txtSalario.getText());
                    newFuncionario.setRua(txtRua.getText());
                    newFuncionario.setBairro(txtBairro.getText());
                    newFuncionario.setNumero(txtNumero.getText());
                    newFuncionario.setCidade(txtCidade.getText());
                    newFuncionario.setTelefone(txtTelefone.getText());
                    newFuncionario.setEmail(txtEmail.getText());
                    try {
                        funcionarioDAO.editFuncionario(newFuncionario);
                    } catch (Exception ex) {
                        Logger.getLogger(FormFuncionarioController.class.getName()).log(Level.SEVERE, null, ex);
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
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    con = DBConnector.getConnection();
                    JasperDesign jdesign = JRXmlLoader.load("src/com/sgo/report/RelatorioFuncionario.jrxml");

                    JRDesignQuery updateQuery = new JRDesignQuery();
                    updateQuery.setText("SELECT * from funcionario ORDER BY id");

                    jdesign.setQuery(updateQuery);
                    JasperReport relatorioCompilado = JasperCompileManager.compileReport(jdesign);

                    JasperPrint relatorioPreenchido = JasperFillManager.fillReport(relatorioCompilado, null, con);

                    JasperViewer.viewReport(relatorioPreenchido, false);

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(FormFuncionarioController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(FormFuncionarioController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JRException ex) {
                    Logger.getLogger(FormFuncionarioController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }     
        });
        
        tblFuncionario.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> 
        {
            if (newVal != null) {
                txtNome.setText(newVal.getNome());
                txtCargo.setText(newVal.getCargo().toString());
                txtCPF.setText(newVal.getCpf());
                txtSalario.setText(newVal.getSalario());
                opcData.setValue(LocalDate.parse(newVal.getDataContrato()));
                txtRua.setText(newVal.getRua());
                txtBairro.setText(newVal.getBairro());
                txtNumero.setText(newVal.getNumero());
                txtCidade.setText(newVal.getCidade());
                txtTelefone.setText(newVal.getTelefone());
                txtEmail.setText(newVal.getEmail());
            }
        });
        boxCargo.getItems().setAll(TipoCargo.values());
        MascaraCampo.mskNumeroCPF(txtCPF);
        MascaraCampo.mskNumero(txtNumero);
        MascaraCampo.mskNumeroTel(txtTelefone);
    }
    
    private int getID(){ // Verifica se  ID está sendo usado.
        List<Funcionario> allFuncionario = funcionarioDAO.getAllFuncionario();
        int maxID = 0;
        
        if(!allFuncionario.isEmpty()){
            for(Funcionario funcionario: allFuncionario){
                if(funcionario.getId()>maxID){
                    
                }
            }
        }
        return ++maxID;
    }
    
    public void refreshTable() { // Atualiza o tableView.
        List<Funcionario> allFuncionario = funcionarioDAO.getAllFuncionario();
        
        funcionarioList.clear();
        
        for(Funcionario funcionario: allFuncionario){
            funcionarioList.add(funcionario);
        }
        tblFuncionario.setItems(funcionarioList);
    }
    
    public void clearTextField(){  //limpar os campos
        txtNome.clear();
        txtCPF.clear();
        txtSalario.clear();
        txtRua.clear();
        txtBairro.clear();
        txtNumero.clear();
        txtCidade.clear();
        txtTelefone.clear();
        txtEmail.clear();
    }
}

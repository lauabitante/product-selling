/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import interfacevendaprodutos.Interfacevendaprodutos;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Cliente;
import view.PrintUtil;

/**
 * FXML Controller class
 *
 * @author lauraabitante
 */
public class PainelMenuClienteController implements Initializable {

    @FXML
    private AnchorPane painelMenuCliente;
    
    @FXML
    private TableView<Cliente> tableViewClientes;
    @FXML
    private TableColumn<Cliente, String> tableColumnCod;
    @FXML
    private TableColumn<Cliente, String> tableColumnNome;
    @FXML
    private TableColumn<Cliente, String> tableColumnCPF;
    @FXML
    private TableColumn<Cliente, String> tableColumnEmail;
    @FXML
    private TableColumn<Cliente, String> tableColumnConta;
    
    private List<Cliente> listaClientes;
    private Cliente clienteSelecionado;
    private ObservableList<Cliente> observableListaClientes;
    private ClienteNegocio clienteNegocio;

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clienteNegocio = new ClienteNegocio();
        if (tableViewClientes != null) {
            carregarTableViewClientes();
        }
    }    
    
    private void carregarTableViewClientes() {
        tableColumnCod.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableColumnConta.setCellValueFactory(new PropertyValueFactory<>("conta"));

        listaClientes = clienteNegocio.listaClientesAtivos(true);
        System.out.println(listaClientes);

        observableListaClientes = FXCollections.observableArrayList(listaClientes);
        tableViewClientes.setItems(observableListaClientes);
    }
    
    public void goToFormularioCliente(ActionEvent event) throws IOException {
        clienteSelecionado = null;
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Interfacevendaprodutos.class.getResource("/view/PainelFormularioCliente.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(painelMenuCliente.getScene().getWindow());
        stage.showAndWait();
        carregarTableViewClientes();
    }
    
    @FXML
    public void goToDepositoCliente(ActionEvent event) throws IOException {
       //        produtoSelecionado = null;
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Interfacevendaprodutos.class.getResource("/view/PainelDepositoCliente.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(painelMenuCliente.getScene().getWindow());
        stage.showAndWait();
//        carregarTableViewProdutos();
    }
    
     @FXML
    public void goToTransferenciaCliente(ActionEvent event) throws IOException {
       //        produtoSelecionado = null;
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Interfacevendaprodutos.class.getResource("/view/PainelTransferenciaCliente.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(painelMenuCliente.getScene().getWindow());
        stage.showAndWait();
//        carregarTableViewProdutos();
    }
    
    @FXML
    public void goToConsultarSaldo(ActionEvent event) throws IOException {
       //        produtoSelecionado = null;
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Interfacevendaprodutos.class.getResource("/view/PainelSaldoCliente.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(painelMenuCliente.getScene().getWindow());
        stage.showAndWait();
//        carregarTableViewProdutos();
    }
    
    @FXML
    public void tratarBotaoExcluir(ActionEvent event) throws IOException {
        
        ButtonType resultado = PrintUtil.printMessageConfirmation("Deseja realmente deletar o cliente?");
        
        if (resultado == ButtonType.YES) {
            clienteSelecionado = tableViewClientes.getSelectionModel().getSelectedItem();
            clienteNegocio.deletarCliente(clienteSelecionado);
            carregarTableViewClientes();
        }
    }

    @FXML
    public void goToMenuPrincipal(ActionEvent event) throws IOException {
        Stage stage = (Stage) painelMenuCliente.getScene().getWindow();
        stage.close();
    }
    
    
    
}

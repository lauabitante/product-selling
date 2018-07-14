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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Cliente;
import view.PrintUtil;

/**
 * FXML Controller class
 *
 * @author lauraabitante
 */
public class PainelVendaSelecaoClienteController implements Initializable {

    @FXML
    private AnchorPane painelSelecaoClientes;
    
    @FXML
    private AnchorPane painelVenda;
    
    @FXML
    private Label labelNomeCliente;

    @FXML
    private TableView<Cliente> tableViewClientes;
    @FXML
    private TableColumn<Cliente, String> tableColumnCod;
    @FXML
    private TableColumn<Cliente, String> tableColumnNome;
    
  
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
        listaClientes = clienteNegocio.listaClientesAtivos(true);

        observableListaClientes = FXCollections.observableArrayList(listaClientes);
        tableViewClientes.setItems(observableListaClientes);
        tableViewClientes.refresh();
    }

    @FXML
    public void goToSelecaoProdutos(ActionEvent event) throws IOException {
        clienteSelecionado = tableViewClientes.getSelectionModel().getSelectedItem();
        if (clienteSelecionado != null) {
            
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(Interfacevendaprodutos.class.getResource("/view/PainelSelecaoProdutosVenda.fxml"));
            Parent root = (Parent) loader.load();
            
            PainelVendaController controller = (PainelVendaController) loader.getController();
            controller.setClienteSelecionado(clienteSelecionado);

            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(painelSelecaoClientes.getScene().getWindow());
            stage.showAndWait();
        } else {
            PrintUtil.printMessageError("Selecione um cliente.");
        }
    }
    
    public void setClienteSelecionado(Cliente cliente) {
        clienteSelecionado = cliente;
    }
    
    @FXML 
    public void tratarBotaoAdicionarNoCarrinho(ActionEvent event) throws IOException {
        
    }
    
    @FXML 
    public void tratarBotaoFinalizarVenda(ActionEvent event) throws IOException {
        
    }
}

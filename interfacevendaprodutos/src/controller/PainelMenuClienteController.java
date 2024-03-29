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
import javafx.scene.control.ButtonType;
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
     * @param url
     * @param rb
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

        observableListaClientes = FXCollections.observableArrayList(listaClientes);
        tableViewClientes.setItems(observableListaClientes);
        tableViewClientes.refresh();
    }

    /**
     * Direciona para o formulário de cadastro de cliente
     * @param event
     * @throws IOException
     */
    @FXML
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

    /**
     * Direciona para o formulário de cliente para edição do registro
     * @param event
     * @throws IOException
     */
    @FXML
    public void goToFormularioClienteEditar(ActionEvent event) throws IOException {
        clienteSelecionado = tableViewClientes.getSelectionModel().getSelectedItem();
        if (clienteSelecionado != null) {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(Interfacevendaprodutos.class.getResource("/view/PainelFormularioCliente.fxml"));
            Parent root = (Parent) loader.load();

            PainelFormularioClienteController controller = (PainelFormularioClienteController) loader.getController();
            controller.setClienteSelecionado(clienteSelecionado);

            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(painelMenuCliente.getScene().getWindow());
            stage.showAndWait();
            carregarTableViewClientes();
        } else {
            PrintUtil.printMessageError("Selecione um cliente.");
        }
    }

    /**
     * Direciona para a tela de depósito na conta de um cliente
     * @param event
     * @throws IOException
     */
    @FXML
    public void goToDepositoCliente(ActionEvent event) throws IOException {
        clienteSelecionado = tableViewClientes.getSelectionModel().getSelectedItem();
        if (clienteSelecionado != null) {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(Interfacevendaprodutos.class.getResource("/view/PainelDepositoCliente.fxml"));
            Parent root = (Parent) loader.load();

            PainelDepositoClienteController controller = (PainelDepositoClienteController) loader.getController();
            controller.setClienteSelecionado(clienteSelecionado);

            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(painelMenuCliente.getScene().getWindow());
            stage.showAndWait();
            carregarTableViewClientes();
        } else {
            PrintUtil.printMessageError("Selecione um cliente.");
        }
    }

    /**
     * Direciona para a tela de transferência entre contas de clientes
     * @param event
     * @throws IOException
     */
    @FXML
    public void goToTransferenciaCliente(ActionEvent event) throws IOException {
        clienteSelecionado = tableViewClientes.getSelectionModel().getSelectedItem();
        if (clienteSelecionado != null) {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(Interfacevendaprodutos.class.getResource("/view/PainelTransferenciaCliente.fxml"));
            Parent root = (Parent) loader.load();

            PainelTransferenciaClienteController controller = (PainelTransferenciaClienteController) loader.getController();
            controller.setClienteSelecionado(clienteSelecionado);

            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(painelMenuCliente.getScene().getWindow());
            stage.showAndWait();

        } else {
            PrintUtil.printMessageError("Selecione um cliente.");
        }
    }

    /**
     * Direciona para tela de visualização de saldo da conta de um cliente
     * @param event
     * @throws IOException
     */
    @FXML
    public void goToConsultarSaldo(ActionEvent event) throws IOException {
        clienteSelecionado = tableViewClientes.getSelectionModel().getSelectedItem();
        if (clienteSelecionado != null) {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(Interfacevendaprodutos.class.getResource("/view/PainelSaldoCliente.fxml"));
            Parent root = (Parent) loader.load();

            PainelSaldoClienteController controller = (PainelSaldoClienteController) loader.getController();
            controller.setClienteSelecionado(clienteSelecionado);

            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(painelMenuCliente.getScene().getWindow());
            stage.showAndWait();
        } else {
            PrintUtil.printMessageError("Selecione um cliente.");
        }
    }

    /**
     * Exclui um registro de cliente
     * @param event
     * @throws IOException
     */
    @FXML
    public void tratarBotaoExcluir(ActionEvent event) throws IOException {
        clienteSelecionado = tableViewClientes.getSelectionModel().getSelectedItem();
        if (clienteSelecionado != null) {
            ButtonType resultado = PrintUtil.printMessageConfirmation("Deseja realmente deletar o cliente?");

            if (resultado == ButtonType.YES) {
                clienteSelecionado = tableViewClientes.getSelectionModel().getSelectedItem();
                clienteNegocio.deletarCliente(clienteSelecionado);
                carregarTableViewClientes();
            }
        } else {
            PrintUtil.printMessageError("Selecione um cliente.");
        }
    }

    /**
     * Retorna ao menu principal da aplicação
     * @param event
     * @throws IOException
     */
    @FXML
    public void goToMenuPrincipal(ActionEvent event) throws IOException {
        Stage stage = (Stage) painelMenuCliente.getScene().getWindow();
        stage.close();
    }
}

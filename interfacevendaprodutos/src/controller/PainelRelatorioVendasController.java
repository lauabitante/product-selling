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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Venda;
import view.PrintUtil;

/**
 * FXML Controller class
 *
 * @author lauraabitante
 */
public class PainelRelatorioVendasController implements Initializable {

    VendaNegocio vendaNegocio = new VendaNegocio();
    
    @FXML
    private AnchorPane painelRelatorioVendas;
    
    @FXML
    private TableView<Venda> tableViewVendas;

    /*
    * FXML Lista Produtos
     */
    @FXML
    private TableColumn<Venda, String> tableColumnCod;
    @FXML
    private TableColumn<Venda, String> tableColumnCliente;
    @FXML
    private TableColumn<Venda, String> tableColumnData;
    @FXML
    private TableColumn<Venda, String> tableColumnTotal;
    
    private List<Venda> listaVendas;
    private ObservableList<Venda> observableListaVendas;
    private Venda vendaSelecionada;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                
        if (tableViewVendas != null) {
            carregarTableViewVendas();
        }
    }    
    
    private void carregarTableViewVendas() {
        tableColumnCod.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnCliente.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));
        tableColumnData.setCellValueFactory(new PropertyValueFactory<>("dataHora"));
        tableColumnTotal.setCellValueFactory(new PropertyValueFactory<>("TotalVenda"));

        listaVendas = vendaNegocio.listar();

        observableListaVendas = FXCollections.observableArrayList(listaVendas);
        tableViewVendas.setItems(observableListaVendas);
    }
    
    /**
     * Direciona para a tela de detalhe do registro de uma venda
     * @param event
     * @throws IOException
     */
    @FXML
    public void goToRelatorioDetalheVenda(ActionEvent event) throws IOException {
        vendaSelecionada = tableViewVendas.getSelectionModel().getSelectedItem();
        if (vendaSelecionada != null) {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(Interfacevendaprodutos.class.getResource("/view/PainelRelatorioVendaDetalhe.fxml"));
            Parent root = (Parent) loader.load();

            PainelRelatorioVendaDetalheController controller = (PainelRelatorioVendaDetalheController) loader.getController();
            controller.setVendaSelecionada(vendaSelecionada);

            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(painelRelatorioVendas.getScene().getWindow());
            stage.showAndWait();
        } else {
            PrintUtil.printMessageError("Selecione uma venda.");
        }
    }
}

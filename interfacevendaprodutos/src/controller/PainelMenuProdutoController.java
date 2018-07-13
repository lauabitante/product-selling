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
import model.Produto;
import view.PrintUtil;

/**
 * FXML Controller class
 *
 * @author lauraabitante
 */
public class PainelMenuProdutoController implements Initializable {

    @FXML
    private AnchorPane MenuProduto;
    @FXML
    private TableView<Produto> tableViewProdutos;
    @FXML
    private TableColumn<Produto, String> tableColumnCod;
    @FXML
    private TableColumn<Produto, String> tableColumnNome;
    @FXML
    private TableColumn<Produto, String> tableColumnPreco;
    
    private List<Produto> listaProdutos;
    private Produto produtoSelecionado;
    private ObservableList<Produto> observableListaProdutos;
    private ProdutoNegocio produtoNegocio;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        produtoNegocio = new ProdutoNegocio();
        if (tableViewProdutos != null) {
            carregarTableViewProdutos();
        }
    } 
    
    private void carregarTableViewProdutos() {
        tableColumnCod.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));

        listaProdutos = produtoNegocio.listar();

        observableListaProdutos = FXCollections.observableArrayList(listaProdutos);
        tableViewProdutos.setItems(observableListaProdutos);
    }
    
    public void goToFormularioProduto(ActionEvent event) throws IOException {
        produtoSelecionado = null;
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Interfacevendaprodutos.class.getResource("/view/PainelFormularioProduto.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(MenuProduto.getScene().getWindow());
        stage.showAndWait();
        carregarTableViewProdutos();
    }
    
    @FXML
    public void goToFormularioProdutoEditar(ActionEvent event) throws IOException {
        produtoSelecionado = tableViewProdutos.getSelectionModel().getSelectedItem();
        if (produtoSelecionado != null) {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(Interfacevendaprodutos.class.getResource("/view/PainelFormularioProduto.fxml"));
            Parent root = (Parent) loader.load();

            PainelFormularioProdutoController controller = (PainelFormularioProdutoController) loader.getController();
            controller.setProdutoSelecionado(produtoSelecionado);

            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(MenuProduto.getScene().getWindow());
            stage.showAndWait();
            
            carregarTableViewProdutos();
        } else {
            PrintUtil.printMessageError("Selecione um produto.");
        }
    }
    
    @FXML
    public void goToMenuPrincipal(ActionEvent event) throws IOException {
        Stage stage = (Stage) MenuProduto.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    public void tratarBotaoExcluir(ActionEvent event) throws IOException {
        Stage stage = (Stage) MenuProduto.getScene().getWindow();
        stage.close();
    }
    
}

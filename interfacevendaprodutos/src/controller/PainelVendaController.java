/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import interfacevendaprodutos.Interfacevendaprodutos;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
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
import model.Produto;
import view.PrintUtil;

/**
 * FXML Controller class
 *
 * @author lauraabitante
 */
public class PainelVendaController implements Initializable {

    @FXML
    private AnchorPane painelVenda;

    @FXML
    private Label labelNomeCliente;

    @FXML
    private TableView<Produto> tableViewCarrinho;

    @FXML
    private TableView<Produto> tableViewProdutos;

    /*
    * FXML Lista Produtos
     */
    @FXML
    private TableColumn<Cliente, String> tableColumnCodProdutoLista;
    @FXML
    private TableColumn<Cliente, String> tableColumnNomeProdutoLista;
    @FXML
    private TableColumn<Cliente, String> tableColumnPrecoProdutoLista;

    /*
    * FXML Lista Carrinho
     */
    @FXML
    private TableColumn<Cliente, String> tableColumnCodProdutoCarrinho;
    @FXML
    private TableColumn<Cliente, String> tableColumnNomeProdutoCarrinho;
    @FXML
    private TableColumn<Cliente, String> tableColumnPrecoProdutoCarrinho;

    private ClienteNegocio clienteNegocio;
    private Cliente clienteSelecionado;

    private ProdutoNegocio produtoNegocio;
    private Produto produtoSelecionado;

    private List<Produto> listaProdutos;
    private ObservableList<Produto> observableListaProdutos;

    private List<Produto> listaCarrinho = new ArrayList<>();
    private ObservableList<Produto> observableListaCarrinho;
    
    private VendaNegocio vendaNegocio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        clienteNegocio = new ClienteNegocio();
        produtoNegocio = new ProdutoNegocio();
        vendaNegocio = new VendaNegocio();
        
        if (tableViewProdutos != null) {
            carregarTableViewProdutos();
        }
        if (tableViewCarrinho != null) {
            carregarTableViewCarrinho();
        }
    }

    private void carregarTableViewProdutos() {
        tableColumnCodProdutoLista.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        tableColumnNomeProdutoLista.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnPrecoProdutoLista.setCellValueFactory(new PropertyValueFactory<>("preco"));

        listaProdutos = produtoNegocio.listar();

        observableListaProdutos = FXCollections.observableArrayList(listaProdutos);
        tableViewProdutos.setItems(observableListaProdutos);
    }

    private void carregarTableViewCarrinho() {
        tableColumnCodProdutoCarrinho.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        tableColumnNomeProdutoCarrinho.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnPrecoProdutoCarrinho.setCellValueFactory(new PropertyValueFactory<>("preco"));

        observableListaCarrinho = FXCollections.observableArrayList(listaCarrinho);
        tableViewCarrinho.setItems(observableListaCarrinho);
    }

    public void setClienteSelecionado(Cliente cliente) {
        clienteSelecionado = cliente;
        labelNomeCliente.setText(clienteSelecionado.getNome());
        vendaNegocio.createVenda(cliente);
    }

    @FXML
    public void tratarBotaoAdicionarNoCarrinho(ActionEvent event) throws IOException {
        produtoSelecionado = tableViewProdutos.getSelectionModel().getSelectedItem();
        if (produtoSelecionado != null) {
            listaCarrinho.add(produtoSelecionado);
            vendaNegocio.adicionarProduto(produtoSelecionado);
            produtoSelecionado = null;
            carregarTableViewCarrinho();
        } else {
            PrintUtil.printMessageError("Selecione um produto.");
        }
    }

    @FXML
    public void tratarBotaoFinalizarVenda(ActionEvent event) throws IOException {
        if (vendaNegocio.venda.getProdutos().size() == 0) {
            PrintUtil.printMessageError("Selecione ao menos um produto");
        }
        else if (clienteSelecionado.getConta().getSaldoConta() < vendaNegocio.venda.getTotalVenda()) {
            PrintUtil.printMessageError("Saldo insuficiente");
        } else {
            vendaNegocio.finalizarVenda();
            PrintUtil.printMessageSuccess("Venda realizada com sucesso!");
            
            Stage stage = (Stage) painelVenda.getScene().getWindow();
            stage.close();
        }
    }
}

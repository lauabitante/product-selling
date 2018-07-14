/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Produto;
import model.Venda;

/**
 * FXML Controller class
 *
 * @author lauraabitante
 */
public class PainelRelatorioVendaDetalheController implements Initializable {

    @FXML
    private AnchorPane painelRelatorioVendaDetalhe;
    
    @FXML
    private TableView<Produto> tableViewProdutos;

    /*
    * FXML Lista Produtos
     */
    @FXML
    private TableColumn<Produto, String> tableColumnCod;
    @FXML
    private TableColumn<Produto, String> tableColumnNome;
    @FXML
    private TableColumn<Produto, String> tableColumnPreco;
    
    @FXML
    private Label labelNomeCliente;
    @FXML
    private Label labelTotalVenda;
    @FXML
    private Label labelDataVenda;
    
    private Venda vendaSelecionada;
    
    private List<Produto> listaProdutos;
    private ObservableList<Produto> observableListaProdutos;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (tableViewProdutos != null) {
            carregarTableViewProdutos();
        }
    }   
    
    private void carregarTableViewProdutos() {
        tableColumnCod.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));

        listaProdutos = listaProdutos != null ? vendaSelecionada.getProdutos() : new ArrayList<>();

        observableListaProdutos = FXCollections.observableArrayList(listaProdutos);
        tableViewProdutos.setItems(observableListaProdutos);
    }
    
    public void setVendaSelecionada(Venda venda) {
        vendaSelecionada = venda;
        labelNomeCliente.setText(venda.getNomeCliente());
        labelDataVenda.setText(String.valueOf(venda.getDataHora()));
        labelTotalVenda.setText(String.valueOf(venda.getTotalVenda()));
        carregarTableViewProdutos();
    }
}

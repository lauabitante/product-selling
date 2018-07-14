/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Venda;

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
    
    /**
     * Initializes the controller class.
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
    
}

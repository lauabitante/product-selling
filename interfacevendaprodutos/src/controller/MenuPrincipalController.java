/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import interfacevendaprodutos.Interfacevendaprodutos;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.PrintUtil;

/**
 * FXML Controller class
 *
 * @author lauraabitante
 */
public class MenuPrincipalController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
//    @FXML
//    private VBox painelMenuCliente;
//    @FXML
//    private AnchorPane painelMenuCliente;
    
    @FXML
    private AnchorPane MenuPrincipal;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    public void tratarBotaoCliente(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Interfacevendaprodutos.class.getResource("/view/PainelMenuCliente.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(MenuPrincipal.getScene().getWindow());
        stage.showAndWait();
    }

    @FXML
    public void tratarBotaoProduto(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Interfacevendaprodutos.class.getResource("/view/PainelMenuProduto.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(MenuPrincipal.getScene().getWindow());
        stage.showAndWait();
    }
    
    @FXML
    public void tratarBotaoVenda(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Interfacevendaprodutos.class.getResource("/view/PainelVendaSelecaoCliente.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(MenuPrincipal.getScene().getWindow());
        stage.showAndWait();
    }

    
}

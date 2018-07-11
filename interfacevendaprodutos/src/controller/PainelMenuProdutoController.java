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

/**
 * FXML Controller class
 *
 * @author lauraabitante
 */
public class PainelMenuProdutoController implements Initializable {

    @FXML
    private AnchorPane MenuProduto;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void goToFormularioProduto(ActionEvent event) throws IOException {
//        produtoSelecionado = null;
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Interfacevendaprodutos.class.getResource("/view/PainelFormularioProduto.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(MenuProduto.getScene().getWindow());
        stage.showAndWait();
//        carregarTableViewProdutos();
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

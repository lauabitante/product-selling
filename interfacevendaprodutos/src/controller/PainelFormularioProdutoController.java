/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Produto;

/**
 * FXML Controller class
 *
 * @author lauraabitante
 */
public class PainelFormularioProdutoController implements Initializable {

    @FXML
    private AnchorPane painelFormularioProduto;
    @FXML
    private TextField textFieldCod;
    @FXML
    private TextField textFieldNome;
    @FXML
    private TextField textFieldPreco;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    public void tratarBotaoSalvar(ActionEvent event) throws IOException {
        Stage stage = (Stage) painelFormularioProduto.getScene().getWindow();
    }

    @FXML
    public void tratarBotaoCancelar(ActionEvent event) throws IOException {
        Stage stage = (Stage) painelFormularioProduto.getScene().getWindow();
        stage.close();
    }

//    public Produto getPacienteSelecionado() {
//        return produtoSelecionado;
//    }
    
}

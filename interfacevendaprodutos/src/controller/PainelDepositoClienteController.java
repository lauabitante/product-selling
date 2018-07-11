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

/**
 * FXML Controller class
 *
 * @author lauraabitante
 */
public class PainelDepositoClienteController implements Initializable {

    @FXML
    private AnchorPane painelDepositoCliente;
    @FXML
    private TextField textFieldValor;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    public void tratarBotaoDepositar(ActionEvent event) throws IOException {
        Stage stage = (Stage) painelDepositoCliente.getScene().getWindow();
    }

    @FXML
    public void tratarBotaoCancelar(ActionEvent event) throws IOException {
        Stage stage = (Stage) painelDepositoCliente.getScene().getWindow();
        stage.close();
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Cliente;

/**
 * FXML Controller class
 *
 * @author lauraabitante
 */
public class PainelSaldoClienteController implements Initializable {

    @FXML
    private AnchorPane painelSaldoCliente;
    @FXML
    private Label labelValor;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    public void setClienteSelecionado(Cliente clienteSelecionado) {
        String valor = String.valueOf(clienteSelecionado.getConta().getSaldoConta());
        labelValor.setText("R$ " + valor);
    }
}

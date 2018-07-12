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
import model.Cliente;
import view.PrintUtil;

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
    
    private Cliente clienteSelecionado;
    private ClienteNegocio clienteNegocio;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clienteNegocio = new ClienteNegocio();
    }    
    
    @FXML
    public void tratarBotaoDepositar(ActionEvent event) throws IOException {
        Stage stage = (Stage) painelDepositoCliente.getScene().getWindow();
        if (textFieldValor.getText().isEmpty() || Double.parseDouble(textFieldValor.getText()) < 1) {
            PrintUtil.printMessageError("Valor inválido.");
        } else {
            Double valor = Double.parseDouble(textFieldValor.getText());
            clienteNegocio.realizarDeposito(clienteSelecionado, valor);
            PrintUtil.printMessageSuccess("Depósito realizado com sucesso!");
            stage.close();
        }
    }

    @FXML
    public void tratarBotaoCancelar(ActionEvent event) throws IOException {
        Stage stage = (Stage) painelDepositoCliente.getScene().getWindow();
        stage.close();
    }

    public void setClienteSelecionado(Cliente clienteSelecionado) {
        this.clienteSelecionado = clienteSelecionado;
    }
}

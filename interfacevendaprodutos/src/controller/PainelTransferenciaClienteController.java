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
public class PainelTransferenciaClienteController implements Initializable {

    @FXML
    private AnchorPane painelTransferenciaCliente;
    @FXML
    private TextField textFieldValor;
    @FXML
    private TextField textFieldClienteDestino;
    
    private Cliente clienteSelecionado;
    private ClienteNegocio clienteNegocio;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clienteNegocio = new ClienteNegocio();
    }    
    
    /**
     * Realiza transferência entre contas de clientes
     * @param event
     * @throws IOException
     */
    @FXML
    public void tratarBotaoTransferir(ActionEvent event) throws IOException {
        
        Stage stage = (Stage) painelTransferenciaCliente.getScene().getWindow();
        if (textFieldValor.getText().isEmpty() || Double.parseDouble(textFieldValor.getText()) < 1) {
            PrintUtil.printMessageError("Valor inválido.");
        } 
        else if (textFieldClienteDestino.getText().isEmpty() || Integer.parseInt(textFieldClienteDestino.getText()) < 1) {
            PrintUtil.printMessageError("Código de cliente inválido.");
        } else {
            Double valor = Double.parseDouble(textFieldValor.getText());
            int codigo = Integer.parseInt(textFieldClienteDestino.getText());
            Cliente clienteDestino = clienteNegocio.buscarPorCodigo(codigo);
            if (clienteSelecionado.getConta().getSaldoConta() < valor) {
                PrintUtil.printMessageError("Saldo insuficiente.");
            }
            else if (clienteDestino == null) {
                PrintUtil.printMessageError("Cliente não encontrado.");
            } else {
                clienteNegocio.realizarTransferencia(clienteSelecionado, clienteDestino, valor);
                PrintUtil.printMessageSuccess("Transferência realizada com sucesso!");
                stage.close();
            }
        }
    }

    /**
     * Retorna ao painel principal de cliente
     * @param event
     * @throws IOException
     */
    @FXML
    public void tratarBotaoCancelar(ActionEvent event) throws IOException {
        Stage stage = (Stage) painelTransferenciaCliente.getScene().getWindow();
        stage.close();
    }
    
    /**
     *
     * @param clienteSelecionado
     */
    public void setClienteSelecionado(Cliente clienteSelecionado) {
        this.clienteSelecionado = clienteSelecionado;
    }
}

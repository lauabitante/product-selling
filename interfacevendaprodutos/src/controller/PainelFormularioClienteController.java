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
import model.Conta;
import view.MaskFieldUtil;
import view.PrintUtil;

/**
 * FXML Controller class
 *
 * @author lauraabitante
 */
public class PainelFormularioClienteController implements Initializable {

    @FXML
    private AnchorPane painelFormularioCliente;
    @FXML
    private TextField textFieldNome;
    @FXML
    private TextField textFieldCPF;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private TextField textFieldConta;

    private ClienteNegocio clienteNegocio;
    private Cliente clienteSelecionado;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clienteNegocio = new ClienteNegocio();
        MaskFieldUtil.cpfField(this.textFieldCPF);
    }

    /**
     * Salva um novo registro de cliente
     * @param event
     * @throws IOException
     */
    @FXML
    public void tratarBotaoSalvar(ActionEvent event) throws IOException {
        Stage stage = (Stage) painelFormularioCliente.getScene().getWindow();

        if (textFieldNome.getText().isEmpty()
                || textFieldEmail.getText().isEmpty()
                || textFieldCPF.getText().isEmpty()
                || textFieldConta.getText().isEmpty()) {
            PrintUtil.printMessageError("Preencha todos os campos!");
        } else {
            
            if (clienteSelecionado == null) {
                Conta conta = new Conta(Integer.parseInt(textFieldConta.getText()), 0);
                Cliente cliente = new Cliente(textFieldNome.getText(), textFieldEmail.getText(), textFieldCPF.getText(), conta);
            
                clienteNegocio.salvarCliente(cliente);
                PrintUtil.printMessageSuccess("Cliente cadastrado com sucesso!");
            } else {
                clienteSelecionado.setNome(textFieldNome.getText());
                clienteSelecionado.setCpf(textFieldCPF.getText());
                clienteSelecionado.setEmail(textFieldEmail.getText());
                clienteSelecionado.getConta().setNumeroConta(Integer.parseInt(textFieldConta.getText()));
                
                clienteNegocio.atualizarDadosCliente(clienteSelecionado);
                PrintUtil.printMessageSuccess("Cliente atualizado com sucesso!");
            }
            stage.close();
        }
    }

    /**
     * Retorna ao painel principal de cliente
     * @param event
     * @throws IOException
     */
    @FXML
    public void tratarBotaoCancelar(ActionEvent event) throws IOException {
        Stage stage = (Stage) painelFormularioCliente.getScene().getWindow();
        stage.close();
    }

    /**
     *
     * @return
     */
    public Cliente getClienteSelecionado() {
        return clienteSelecionado;
    }

    /**
     *
     * @param clienteSelecionado
     */
    public void setClienteSelecionado(Cliente clienteSelecionado) {
        this.clienteSelecionado = clienteSelecionado;
        textFieldNome.setText(clienteSelecionado.getNome());
        textFieldCPF.setText(clienteSelecionado.getCpf());
        textFieldEmail.setText(clienteSelecionado.getEmail());
        textFieldConta.setText(String.valueOf(clienteSelecionado.getConta().getNumeroConta()));
    }

}

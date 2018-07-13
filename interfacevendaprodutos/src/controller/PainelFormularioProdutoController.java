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
import view.PrintUtil;

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
    
    private ProdutoNegocio produtoNegocio;
    private Produto produtoSelecionado;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        produtoNegocio = new ProdutoNegocio();
    } 
    
    @FXML
    public void tratarBotaoSalvar(ActionEvent event) throws IOException {
        Stage stage = (Stage) painelFormularioProduto.getScene().getWindow();

        if (textFieldNome.getText().isEmpty()
                || textFieldCod.getText().isEmpty()
                || textFieldPreco.getText().isEmpty()) {
            PrintUtil.printMessageError("Preencha todos os campos!");
        } else {
            if (produtoSelecionado == null) {
                int cod = Integer.parseInt(textFieldCod.getText());
                String nome = textFieldNome.getText();
                double preco = Double.parseDouble(textFieldPreco.getText());
                Produto produto = new Produto(cod, nome, preco);
                produtoNegocio.salvar(produto);
                PrintUtil.printMessageSuccess("Produto cadastrado com sucesso!");
            } else {
                produtoSelecionado.setNome(textFieldNome.getText());
                produtoSelecionado.setPreco(Double.parseDouble(textFieldPreco.getText()));
                
                produtoNegocio.atualizar(produtoSelecionado);
                PrintUtil.printMessageSuccess("Produto atualizado com sucesso!");
            }
            stage.close();
        }
    }

    @FXML
    public void tratarBotaoCancelar(ActionEvent event) throws IOException {
        Stage stage = (Stage) painelFormularioProduto.getScene().getWindow();
        stage.close();
    }

    public void setProdutoSelecionado(Produto produtoSelecionado) {
        this.produtoSelecionado = produtoSelecionado;
        textFieldNome.setText(produtoSelecionado.getNome());
        textFieldCod.setText(String.valueOf(produtoSelecionado.getCodigo()));
        textFieldCod.setEditable(false);
        textFieldPreco.setText(String.valueOf(produtoSelecionado.getPreco()));
    }
    
}

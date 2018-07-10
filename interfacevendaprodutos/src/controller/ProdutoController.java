/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ProdutoDAO;
import dao.impl_BD.BDException;
import dao.impl_BD.ProdutoDAO_BD;
import java.util.List;
import java.util.Scanner;
import model.Produto;

/**
 *
 * @author lauraabitante
 */
public class ProdutoController {
    
    private ProdutoDAO produtoDAO = new ProdutoDAO_BD();

    /**
     * Cadastra um produto a partir do input do usuário
     */
    public void cadastrarProduto() {    
        int codProduto = this.solicitarDadoInt("Digite o código do produto: ");
        String nomeProduto = this.solicitarDadoString("Digite o nome do produto: ");
        double precoProduto = this.solicitarDadoDouble("Digite o preço do produto: ");
        
        Produto produto = new Produto(codProduto, nomeProduto, precoProduto);
        produtoDAO.salvar(produto);
    }
    
     public String solicitarDadoString(String mensagem) {
        Scanner scan = new Scanner(System.in);
        String dado;
        do {
            System.out.print(mensagem);
            dado = scan.nextLine().trim();
        } while (dado.length() == 0);

        return dado;
    }
     
     public Double solicitarDadoDouble(String mensagem) {
        Scanner scan = new Scanner(System.in);
        Double dado = 999999.99;
        do {
            try {
                System.out.print(mensagem);
                dado = scan.nextDouble();
            } catch (NumberFormatException ex) {
                System.out.println("Valor inválido.");
                scan.next();
            }
        } while (dado == 999999.99);

        return dado;
    }
     
     public int solicitarDadoInt(String mensagem) {
        Scanner scan = new Scanner(System.in);
        int dado = 9;
        do {
            try {
                System.out.print(mensagem);
                dado = scan.nextInt();
            } catch (NumberFormatException ex) {
                System.out.println("Valor inválido.");
                scan.next();
            }
        } while (dado == 9);

        return dado;
    }

    /**
     * Lista de produtos cadastrados
     */
    public void listarProdutos() {
        List<Produto> produtos = produtoDAO.listar();
        if (produtos.isEmpty()) {
            System.out.println("\nNENHUM PRODUTO CADASTRADO.");
        } else {
            System.out.println("");
            separador();
            System.out.println("LISTA DE PRODUTOS");
            separador();
            for (int i = 0; i < produtos.size(); i++) {
                Produto produto = produtos.get(i);
                System.out.println("COD: " + produto.getCodigo() + "     " + produto.toString());
            }
            separador();
        }
    }
    
    public void atualizarProduto() {
        Produto produto = getProduto();
        String nomeProduto = this.solicitarDadoString("Digite o nome do produto: ");
        double precoProduto = this.solicitarDadoDouble("Digite o preço do produto: ");
        produto.setNome(nomeProduto);
        produto.setPreco(precoProduto);
        produtoDAO.atualizar(produto);
    }
    
    public void deletarProduto() {
        Produto produto = getProduto();
        Scanner scan = new Scanner(System.in);
        int opcao;
        System.out.println("Confirma exclusão? \n[1]Sim \n[2]Não");
        opcao = scan.nextInt();
        
        switch (opcao) {
            case 1:
                produtoDAO.deletar(produto);
                System.out.println("Produto excluído com sucesso!");
                break;
            case 2:
                System.out.println("Operação cancelada!");
                break;
            default:
                System.out.println("Opção inválida");
                break;
        }
    }

    /**
     * Obtém um produto a partir do input do usuário
     *
     * @return Retorna um produto ou null caso não exista
     */
    public Produto getProduto() {
        Scanner scan = new Scanner(System.in);
        listarProdutos();
        System.out.print("\nDigite o código do produto: ");
        int codProduto = scan.nextInt();
        try {
            return produtoDAO.buscarPorCodigo(codProduto);
        }
        catch (BDException ex) {
            System.out.println(ex);
            return null;
        }        
    }

    private void separador() {
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
    }
}

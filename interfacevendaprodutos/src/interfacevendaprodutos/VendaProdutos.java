/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfacevendaprodutos;

import controller.ClienteNegocio;
import controller.ProdutoController;
import controller.VendaController;
import java.time.LocalDate;
import java.util.*;
import model.Cliente;
import model.Produto;
import model.Venda;
import view.Relatorio;

/**
 *
 * @author lauraabitante
 */
public class VendaProdutos {

    static int opcaoMenu = 99;

    /**
     * Instância da classe ClienteNegocio
     */
    public static ClienteNegocio clienteController = new ClienteNegocio();

    /**
     * Instância da classe ProdutoController
     */
    public static ProdutoController produtoController = new ProdutoController();

    /**
     * Instância da classe VendaController
     */
    public static VendaController vendaController = new VendaController();

    /**
     * Método principal da aplicação
     *
     * @param args
     */
    public static void main(String[] args) {

        do {
            menu();
        } while (opcaoMenu != 0);
    }

    /**
     * Menu principal
     */
    public static void menu() {
        try {
            Scanner scan = new Scanner(System.in);
            System.out.println("\nMENU: \n[1]Clientes  \n[2]Produtos  \n[3]Realizar Venda   \n[4]Relatórios   \n[0]Encerrar");
            System.out.print("\nEscolha a opção: ");
            opcaoMenu = scan.nextInt();

            switch (opcaoMenu) {
                case 1:
                    menuCliente();
                    break;
                case 2:
                    menuProduto();
                    break;
                case 3:
                    realizarVenda();
                    break;
                case 4:
                    gerarRelatorio();
                    break;
                case 0:
                    System.out.println("FIM");
                    break;
                default:
                    System.out.println("Inválido");
            }
        } catch (InputMismatchException ex) {
            System.out.println("Inválido.");
        }
    }

    /**
     * Menu de clientes
     */
    public static void menuCliente() {
        Scanner scan = new Scanner(System.in);

        System.out.println("\nMENU CLIENTE: \n[1]Cadastrar Cliente  \n[2]Listar Clientes   \n[3]Atualizar Cliente   \n[4]Excluir Cliente   \n[5]Depositar   \n[6]Transferir   \n[7]Consultar Saldo   \n[0]Sair");
        System.out.print("\nEscolha a opção: ");
        int opcao = scan.nextInt();

        switch (opcao) {
            case 1:
                clienteController.cadastrarCliente();
                break;
            case 2:
                clienteController.listarClientesAtivos();
                break;
            case 3:
                clienteController.atualizarDadosCliente();
                break;
            case 4:
                clienteController.deletarCliente();
                break;
            case 5:
                clienteController.solicitarDeposito();
                break;
            case 6:
                clienteController.solicitarTransferencia();
                break;
            case 7:
                clienteController.consultarSaldo();
                break;
            case 0:
                menu();
                break;
            default:
                System.out.println("\nOpção inválida.");
        }
    }

    /**
     * Menu de produtos
     */
    public static void menuProduto() {
        Scanner scan = new Scanner(System.in);

        System.out.println("\nMENU PRODUTO: \n[1]Cadastrar Produto   \n[2]Listar Produtos   \n[3]Atualizar Produto   \n[4]Excluir Produto   \n[0]Sair");
        System.out.print("\nEscolha a opção: ");
        int opcao = scan.nextInt();

        switch (opcao) {
            case 1:
                produtoController.cadastrarProduto();
                break;
            case 2:
                produtoController.listarProdutos();
                break;
            case 3:
                produtoController.atualizarProduto();
                break;
            case 4:
                produtoController.deletarProduto();
                break;
            case 0:
                menu();
                break;
            default:
                System.out.println("\nOpção inválida");
        }
    }

    private static void realizarVenda() {
        Scanner scan = new Scanner(System.in);
        Cliente c = clienteController.getCliente();

        if (c != null) {

            Venda venda = new Venda(vendaController.vendaDAO.listar().size(), LocalDate.now(), c);
            int opcaoVenda;

            do {
                System.out.println("\nMENU VENDA: \n[1]Selecionar Produto   \n[2]Finalizar Venda   \n[0]Cancelar");
                System.out.print("\nEscolha a opção: ");

                opcaoVenda = scan.nextInt();
                switch (opcaoVenda) {
                    case 1:
                        Produto p = produtoController.getProduto();
                        if (p != null) {
                            venda.adicionarProduto(p);
                        }
                        break;
                    case 2:
                        if (venda.getTotalVenda() <= c.getConta().getSaldoConta()) {
                            if (venda.getProdutos().isEmpty()) {
                                System.out.println("Selecione um produto.");
                            } else {
                                c.getConta().retirar(venda.getTotalVenda());
                                vendaController.salvar(venda);
                                clienteController.atualizarSaldoCliente(c);
                                System.out.println("Venda finalizada com sucesso!");
                                menu();
                                opcaoVenda = 0;
                            }
                        } else {
                            System.out.println("Saldo insuficiente.");
                        }
                        break;
                    case 0:
                        menu();
                        break;
                    default:
                        System.out.println("\nOpção inválida.");
                }
            } while (opcaoVenda != 0);

        } else {
            System.out.println("\nTente novamente!");
        }
    }

    /**
     * Apresenta menu para gerar um relatório
     */
    public static void gerarRelatorio() {
        Relatorio relatorio = new Relatorio();
        Scanner scan = new Scanner(System.in);
        int opcao;
        do {
            System.out.println("\nGERAR RELATÓRIO: \n[1]Relatório Geral de Vendas \n[2]Relatório de Vendas Por Cliente Ativo \n[3]Relatório de uma Venda \n[0]Sair");
            System.out.print("\nEscolha a opção: ");
            opcao = scan.nextInt();
            switch (opcao) {
                case 1:
                    relatorio.gerarRelatorioGeralVenda();
                    break;
                case 2:
                    Cliente c = clienteController.getCliente();
                    if (c != null) {
                        relatorio.gerarRelatorioVendasCliente(c);
                    }
                    break;
                case 3:
                    Venda v = vendaController.getVenda();
                    if (v != null) {
                        relatorio.gerarRelatorioVenda(v);
                    }
                    break;
                case 0:
                    menu();
                    break;
                default:
                    System.out.println("\nOpção inválida.");
                    break;
            }
        } while (opcao != 0);
    }
}

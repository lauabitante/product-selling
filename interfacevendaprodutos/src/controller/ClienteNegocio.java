/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ClienteDAO;
import dao.impl_BD.ClienteDAO_BD;
import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import model.Cliente;
import model.Conta;

/**
 *
 * @author lauraabitante
 */
public class ClienteNegocio {

    private DecimalFormat df = new DecimalFormat("0.##");    
    private ClienteDAO clienteDAO = new ClienteDAO_BD();

    /**
     * Cadastra um cliente a partir do input do usuário
     */
    public void cadastrarCliente() {
        String nomeCliente = this.solicitarDadoString("\nNome: ", false);
        
        String cpfCliente;
        do {
            cpfCliente = this.solicitarDadoString("CPF: ", false);
        } while (verificarCPFexistente(cpfCliente) == true);
        
        String emailCliente = this.solicitarDadoString("Email: ", false);
        int numeroContaCliente = this.solicitarDadoInt("Conta: ");
        Double saldoContaCliente = this.solicitarDadoDouble("Saldo: ");

        Conta conta = new Conta(numeroContaCliente, saldoContaCliente);
        Cliente cliente = new Cliente(nomeCliente, emailCliente, cpfCliente, conta);
        salvarCliente(cliente);
    }
    
    public void salvarCliente(Cliente cliente) {
        clienteDAO.salvar(cliente);
    }

    public String solicitarDadoString(String mensagem, boolean aceitaNull) {
        Scanner scan = new Scanner(System.in);
        String dado;
        do {
            System.out.print(mensagem);
            dado = scan.nextLine().trim();
        } while (dado.length() == 0 && aceitaNull == false);

        return dado;
    }

    public int solicitarDadoInt(String mensagem) {
        Scanner scan = new Scanner(System.in);
        int dado = 0;
        do {
            try {
                System.out.print(mensagem);
                dado = scan.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Valor inválido.");
                scan.next();
            }
        } while (dado == 0);

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

    /**
     * Lista clientes cadastrados
     */
    public void listarClientesAtivos() {
        System.out.println("\n");
        List<Cliente> clientes = clienteDAO.listar(true);
        if (clientes.isEmpty()) {
            separador();
            System.out.println("NENHUM CLIENTE CADASTRADO");
        } else {
            System.out.println("");
            separador();
            System.out.println("LISTA DE CLIENTES");
            separador();
            for (int i = 0; i < clientes.size(); i++) {
                Cliente cliente = clientes.get(i);
                System.out.println(cliente.toString());
            }
        }
        separador();
    }
    
    public List<Cliente> listaClientesAtivos(boolean ativo) {
        return clienteDAO.listar(ativo);
    }
    
    public void atualizarDadosCliente() {
        Cliente cliente = getCliente();
        System.out.println("Preencha os campos que deseja alterar. Pressione enter caso não tenha alteração.");
        String nome = this.solicitarDadoString("Nome: ", true);
        String email = this.solicitarDadoString("Email: " , true);
        String cpf = this.solicitarDadoString("CPF: " , true);
        
        if (!nome.isEmpty()) {
            cliente.setNome(nome);
        }
        if (!email.isEmpty()) {
            cliente.setEmail(email);
        }
        if (!cpf.isEmpty()) {
            cliente.setCpf(cpf);
        }
        clienteDAO.atualizar(cliente);
    }
     
     public void atualizarSaldoCliente(Cliente cliente) {
         clienteDAO.atualizar(cliente);
         
     }
    
    public void deletarCliente() {
        Cliente cliente = getCliente();
        Scanner scan = new Scanner(System.in);
        int opcao;
        System.out.println("Confirma exclusão? \n[1]Sim \n[2]Não");
        opcao = scan.nextInt();
        
        switch (opcao) {
            case 1:
                clienteDAO.deletar(cliente);
                System.out.println("Cliente excluído com sucesso!");
                break;
            case 2:
                System.out.println("Operação cancelada!");
                break;
            default:
                System.out.println("Opção inválida");
                break;
        }
    }
    
    public void deletarCliente(Cliente cliente) {
        clienteDAO.deletar(cliente);
    }
    
    /**
     * Solicita um depósito a partir do input do usuário
     */
    public void solicitarDeposito() {
        Cliente c = getCliente();
        if (c != null) {
            Scanner scan = new Scanner(System.in);
            System.out.print("\nDigite o valor para depósito: ");
            Double valor = scan.nextDouble();
            realizarDeposito(c, valor);
        }
    }

    /**
     * Analisa e confirma um depósito
     *
     * @param c cliente destino
     * @param valor valor do depósito
     */
    public void realizarDeposito(Cliente c, double valor) {
        if (valor > 0) {
            c.getConta().depositar(valor);
            clienteDAO.atualizar(c);
            Double saldoAtual = c.getConta().getSaldoConta();
            System.out.println("\nDepósito realizado com sucesso! Saldo Atual: " + df.format(saldoAtual));
        } else {
            System.out.println("\nValor inválido.");
        }
    }

    /**
     * Solicita uma transferência a partir do input do usuário
     */
    public void solicitarTransferencia() {
        Scanner scan = new Scanner(System.in);
        System.out.print("\nCliente origem: ");
        Cliente cOrigem = getCliente();
        System.out.print("\nCliente destino: ");
        Cliente cDestino = getCliente();

        if (cOrigem != null && cDestino != null && cOrigem.getCpf() != cDestino.getCpf()) {
            System.out.print("\nDigite o valor a ser tranferido: ");
            double valorTransferencia = scan.nextDouble();
            if (cOrigem.getConta().getSaldoConta() >= valorTransferencia) {
                realizarTransferencia(cOrigem, cDestino, valorTransferencia);
            } else {
                System.out.println("Saldo insuficiente.");
            }
        } else {
            System.out.println("\nTransferência inválida.");
        }
    }

    /**
     * Analisa e confirma uma transferência.
     *
     * @param cOrigem cliente origem
     * @param cDestino cliente destino
     * @param valorTransferencia valor da transferência
     */
    public void realizarTransferencia(Cliente cOrigem, Cliente cDestino, double valorTransferencia) {
        if (valorTransferencia > 0) {
            cOrigem.getConta().retirar(valorTransferencia);
            clienteDAO.atualizar(cOrigem);
            cDestino.getConta().depositar(valorTransferencia);
            clienteDAO.atualizar(cDestino);
            System.out.println("\nTransferência realizada com sucesso!");
        } else {
            System.out.println("\nValor inválido.");
        }
    }

    /**
     * Consulta saldo do cliente
     */
    public void consultarSaldo() {
        Cliente c = getCliente();
        if (c != null) {
            System.out.println("Saldo: " + df.format(c.getConta().getSaldoConta()));
        }
    }

    /**
     * Obtém um cliente a partir do input do usuário
     *
     * @return Retorna um cliente ou null caso não exista
     */
    public Cliente getCliente() {
        Scanner scan = new Scanner(System.in);
        List<Cliente> clientesAtivos = clienteDAO.listar(true);
        listarClientesAtivos();
        if (!clientesAtivos.isEmpty()) {
            System.out.print("\nDigite o código do cliente: ");
            int codigo = scan.nextInt();
            boolean clienteAtivo = false;
            for(int i=0; i<clientesAtivos.size(); i++) {
                Cliente c = clientesAtivos.get(i);
                if(c.getId() == codigo) {
                    clienteAtivo = true;
                }
            }
            if(clienteAtivo) {
                return clienteDAO.buscarPorID(codigo);
            } else {
                System.out.println("Cliente não encontrado!");
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Verifica se um CPF já está cadastrado para um cliente
     *
     * @param cpf CPF do cliente
     * @return Retorna true se o CPF já está cadastrado
     */
    public boolean verificarCPFexistente(String cpf) {
        Cliente cliente = clienteDAO.buscaPorCPF(cpf);
        return cliente != null;
    }

    private void separador() {
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.VendaDAO;
import dao.impl_BD.VendaDAO_BD;
import static interfacevendaprodutos.VendaProdutos.vendaController;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import model.Cliente;
import model.Produto;
import model.Venda;

/**
 *
 * @author lauraabitante
 */
public class VendaNegocio {

    /**
     * Lista de vendas
     */
    public VendaDAO vendaDAO = new VendaDAO_BD();
    public Venda venda;

    /**
     * Lista as vendas realizadas
     */
    public void listarVendas() {
        System.out.println("\n");
        separador();
        List<Venda> vendas = vendaDAO.listar();
        if (vendas.isEmpty()) {
            System.out.println("NENHUMA VENDA REGISTRADA");
        } else {
            System.out.println("LISTA DE VENDAS");
            separador();
            for (int i = 0; i < vendas.size(); i++) {
                Venda venda = vendas.get(i);
                System.out.println(venda.toString());
            }
        }
        separador();
    }
    
    public void salvar(Venda venda) {
        vendaDAO.salvar(venda);
    }

    /**
     * Obtém uma venda a partir do input do usuário
     *
     * @return Retorna uma venda ou null caso não exista
     */
    public Venda getVenda() {
        Scanner scan = new Scanner(System.in);
        listarVendas();
        if (!vendaDAO.listar().isEmpty()) {
            System.out.print("\nDigite o código da venda: ");
            int codigo = scan.nextInt();
            return vendaDAO.buscarPorID(codigo);
        } else {
            return null;
        } 
    }
    
    private void separador() {
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
    }
     
     public void createVenda(Cliente cliente) {
        venda = new Venda(vendaController.vendaDAO.listar().size(), LocalDate.now(), cliente);
     }
     
     public void adicionarProduto(Produto produto) {
        venda.adicionarProduto(produto);
     }
     
     public void finalizarVenda() {
        venda.getCliente().getConta().retirar(venda.getTotalVenda());
        vendaController.salvar(venda);
        ClienteNegocio clienteNegocio = new ClienteNegocio();
        clienteNegocio.atualizarSaldoCliente(venda.getCliente());
     }
}

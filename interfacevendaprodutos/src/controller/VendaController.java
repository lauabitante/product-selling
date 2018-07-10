/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.VendaDAO;
import dao.impl_BD.VendaDAO_BD;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Venda;

/**
 *
 * @author lauraabitante
 */
public class VendaController {

    /**
     * Lista de vendas
     */
    public VendaDAO vendaDAO = new VendaDAO_BD();

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
}

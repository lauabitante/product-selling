/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.VendaDAO;
import dao.impl_BD.VendaDAO_BD;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.Cliente;
import model.Produto;
import model.Venda;

/**
 *
 * @author lauraabitante
 */
public class Relatorio {

    private DecimalFormat df = new DecimalFormat("0.##");
    VendaDAO vendaDAO = new VendaDAO_BD();
    
    /**
     * Gera o relatório geral de vendas
     * @param vendas lista de vendas a ser impressa no relatório
     */
    public void gerarRelatorioGeralVenda() {
        List<Venda> vendas = vendaDAO.listar();
        if (vendas.isEmpty()) {
            cabecalho("NENHUMA VENDA REGISTRADA");
        } else {
            cabecalho("RELATÓRIO GERAL DE VENDAS");
            for (int i = 0; i < vendas.size(); i++) {
                imprimirVendaDetalhada(vendas.get(i));
            }
        }
    }

    /**
     * Gera o relatório de uma venda
     * @param venda venda a ser impressa no relatório
     */
    public void gerarRelatorioVenda(Venda venda) {
        cabecalho("RELATÓRIO DE VENDA");
        imprimirVendaDetalhada(venda);
    }

    /**
     * Gera o relatório de vendas de um cliente
     * @param cliente cliente da venda
     * @param vendas lista de todas as vendas da aplicação
     */ 
    public void gerarRelatorioVendasCliente(Cliente cliente) {
        cabecalho("RELATÓRIO DE VENDAS POR CLIENTE: " + cliente.getNome());
        List<Venda> vendas = vendaDAO.buscarPorIdCliente(cliente.getId());
        for (int i = 0; i < vendas.size(); i++) {
            imprimirVendaCliente(vendas.get(i));
        }
    }

    private void imprimirVendaDetalhada(Venda venda) {
        ArrayList<Produto> produtosImpressos = new ArrayList<>();
        System.out.println("VENDA: " + venda.getId() + "\t|\tDATA: " + venda.getDataHora() + "\t|\tNOME: " + venda.getCliente().getNome());
        separador();
        for (int i = 0; i < venda.getProdutos().size(); i++) {
            Produto p = venda.getProdutos().get(i);
            if (!produtosImpressos.contains(p)) {
                int quantidade = Collections.frequency(venda.getProdutos(), p);
                System.out.println("PRODUTO: " + p.getNome() + "\t|\tQUANTIDADE: " + quantidade + " \t|\tPREÇO: R$" + df.format(p.getPreco()) + "\t|\tTOTAL: R$" + df.format(p.getPreco() * quantidade));
                produtosImpressos.add(p);
            }
        }
        separador();
        System.out.println("TOTAL: R$" + venda.getTotalVenda());
        separador();
        System.out.println("\n\n");
    }

    private void imprimirVendaCliente(Venda venda) {
        System.out.println("VENDA: " + venda.getId() + "\t|\tDATA: " + venda.getDataHora() + "\t|\tTOTAL: R$" + venda.getTotalVenda());
        separador();
    }

    private void separador() {
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
    }

    private void cabecalho(String titulo) {
        System.out.println("\n");
        separador();
        System.out.println(titulo);
        separador();
    }

}

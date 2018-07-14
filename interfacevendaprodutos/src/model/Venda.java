/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author lauraabitante
 */
public class Venda {

    private int id;
    private LocalDate dataHora;
    private Cliente cliente;
    private ArrayList<Produto> produtos = new ArrayList<>();

    /**
     * Construtor da classe Venda
     *
     * @param id id da venda
     * @param dataHora data e hora da venda
     * @param cliente cliente da venda
     */
    public Venda(int id, LocalDate dataHora, Cliente cliente) {
        this.id = id;
        this.dataHora = dataHora;
        this.cliente = cliente;
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Data e hora da venda
     *
     * @return Retorna a data e a hora de uma venda
     */
    public LocalDate getDataHora() {
        return dataHora;
    }
    
    public void setDataHora(LocalDate dataHora) {
        this.dataHora = dataHora;
    }

    /**
     * Cliente
     *
     * @return Retorna o cliente de uma venda
     */
    public Cliente getCliente() {
        return cliente;
    }
    
     public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
     
    public String getNomeCliente() {
        return cliente.getNome();
    }

    /**
     * Lista de produtos da venda
     *
     * @return Retorna a lista de produtos de uma venda
     */
    public ArrayList<Produto> getProdutos() {
        return produtos;
    }
    
    public void setProdutos(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }

    /**
     * Adiciona um produto na venda
     *
     * @param p produto a ser adicionado em uma venda
     */
    public void adicionarProduto(Produto p) {
        produtos.add(p);
    }

    /**
     * Total da venda
     *
     * @return Retorna o valo total de uma venda
     */
    public double getTotalVenda() {
        double total = 0.0;
        for (int i = 0; i < produtos.size(); i++) {
            total += produtos.get(i).getPreco();
        }
        return total;
    }

    @Override
    public String toString() {
        return "|COD: " + id + "\t|\tDATA: " + dataHora + "\t|\tNOME: " + cliente.getNome() + "\t|\tQUANTIDADE DE PRODUTOS: " + produtos.size();
    }
}
